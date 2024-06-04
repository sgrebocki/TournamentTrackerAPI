package com.TournamentTracker.domain.user;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
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

@Service
@RequiredArgsConstructor
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
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    public UserDto create(UserCreateDto userCreateDto) {
        checkIfUserAlreadyExists(userCreateDto.getUsername());
        User user = userMapper.toEntity(userCreateDto);
        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(Authority.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto update(UserDto userDto, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDto.getUsername());
                    existingUser.setFirstName(userDto.getFirstName());
                    existingUser.setLastName(userDto.getLastName());
                    existingUser.setTeam(userDto.getTeamId() != null ? new Team(userDto.getTeamId()) : null);
                    existingUser.setAuthorities(userDto.getAuthorities());
                    return userMapper.toDto(userRepository.save(existingUser));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        UserDto userDto = getById(id);
        userDto.getAuthorities().clear();
        userRepository.save(userMapper.toEntity(userDto));
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteMany(List<Long> ids){
        for(Long id : ids) {
            UserDto userDto = getById(id);

            userDto.getAuthorities().clear();
            userRepository.save(userMapper.toEntity(userDto));
        }
        userRepository.deleteAllById(ids);
    }

    @Transactional
    public UserDto addAuthority(Long userId, Authority authority) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.getAuthorities().add(authority);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    @Transactional
    public UserDto removeAuthority(Long userId, Authority authority) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.getAuthorities().remove(authority);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    @Transactional
    public UserDto updateAuthorities(Long userId, Set<Authority> authorities) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setAuthorities(authorities);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    @Transactional
    public AuthUserDto getAccountParameters() {
        return authService.getCurrentUser();
    }

    @Transactional
    public UserDto changePassword(String oldPassword, String newPassword) {
        return userRepository.findById(authService.getCurrentUser().getId())
                .map(user -> {
                    checkIfOldPasswordIsValid(oldPassword, user);
                    checkIfNewPasswordIsDifferentThanOldPassword(oldPassword, newPassword, user);
                    user.setPassword(passwordEncoder.encode(newPassword));
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + authService.getCurrentUser().getId() + " not found"));
    }

    @Transactional
    public UserDto changeUsername(String newUsername) {
        checkIfUsernameIsValid(newUsername);
        checkIfUserAlreadyExists(newUsername);
        return userRepository.findById(authService.getCurrentUser().getId())
                .map(user -> {
                    user.setUsername(newUsername);
                    return userMapper.toDto(userRepository.save(user));
                }).orElseThrow(() -> new EntityNotFoundException("User with id " + authService.getCurrentUser().getId() + " not found"));
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
            throw new IllegalArgumentException("Username should be an email");
        }
    }

    private void checkIfUserAlreadyExists(String username) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException(String.format("Użytkownik z loginem '%s' już istnieje.", username));
        }
    }

    private void checkIfOldPasswordIsValid(String oldPassword, User user) {
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("Old password is not valid");
        }
    }

    private void checkIfNewPasswordIsDifferentThanOldPassword(String oldPassword, String newPassword, User user) {
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password should be different than the old one");
        }
    }

}
