package com.TournamentTracker.domain.user;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import com.TournamentTracker.domain.user.model.User;
import com.TournamentTracker.domain.user.model.UserCreateDto;
import com.TournamentTracker.domain.user.model.UserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.TournamentTracker.util.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, id)));
    }

    public UserDto create(UserCreateDto userCreateDto) {
        checkIfUserAlreadyExists(userCreateDto.getUsername());
        User user = userMapper.toEntity(userCreateDto);
        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(Authority.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDto.getUsername());
                    existingUser.setFirstName(userDto.getFirstName());
                    existingUser.setLastName(userDto.getLastName());
                    existingUser.setTeam(userDto.getTeamId() != null ? new Team(userDto.getTeamId()) : null);
                    existingUser.setAuthorities(userDto.getAuthorities());
                    return userMapper.toDto(userRepository.save(existingUser));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, id)));
    }

    public void deleteById(Long id) {
        UserDto userDto = getById(id);
        userDto.getAuthorities().clear();
        userRepository.save(userMapper.toEntity(userDto));
        userRepository.deleteById(id);
    }

    public void deleteMany(List<Long> ids){
        for(Long id : ids) {
            UserDto userDto = getById(id);

            userDto.getAuthorities().clear();
            userRepository.save(userMapper.toEntity(userDto));
        }
        userRepository.deleteAllById(ids);
    }

    public UserDto addAuthority(Long userId, Authority authority) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.getAuthorities().add(authority);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
    }

    public UserDto removeAuthority(Long userId, Authority authority) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.getAuthorities().remove(authority);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
    }

    public UserDto updateAuthorities(Long userId, Set<Authority> authorities) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setAuthorities(authorities);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, userId)));
    }

    public AuthUserDto getAccountParameters() {
        return authService.getCurrentUser();
    }

    public UserDto changePassword(String oldPassword, String newPassword) {
        return userRepository.findById(authService.getCurrentUser().getId())
                .map(user -> {
                    checkIfOldPasswordIsValid(oldPassword, user);
                    checkIfNewPasswordIsDifferentThanOldPassword(oldPassword, newPassword, user);
                    user.setPassword(passwordEncoder.encode(newPassword));
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, authService.getCurrentUser().getId())));
    }

    public UserDto changeUsername(String newUsername) {
        checkIfUsernameIsValid(newUsername);
        checkIfUserAlreadyExists(newUsername);
        return userRepository.findById(authService.getCurrentUser().getId())
                .map(user -> {
                    user.setUsername(newUsername);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_ID, authService.getCurrentUser().getId())));
    }

    public void setTeamForUser(Long userId, Team team) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setTeam(team);
                    userRepository.save(user);
                });
    }

    private void checkIfUsernameIsValid(String username) {
        if (!username.contains("@") || !username.contains(".")) {
            throw new IllegalArgumentException(USERNAME_SHOULD_BE_EMAIL);
        }
    }

    private void checkIfUserAlreadyExists(String username) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException(String.format(USER_ALREADY_EXISTS, username));
        }
    }

    private void checkIfOldPasswordIsValid(String oldPassword, User user) {
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException(OLD_PASSWORD_DOES_NOT_MATCH);
        }
    }

    private void checkIfNewPasswordIsDifferentThanOldPassword(String oldPassword, String newPassword, User user) {
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException(NEW_PASSWORD_SHOULD_BE_DIFFERENT);
        }
    }

}
