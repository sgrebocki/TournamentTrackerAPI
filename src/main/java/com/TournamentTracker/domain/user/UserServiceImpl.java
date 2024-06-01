package com.TournamentTracker.domain.user;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.user.model.AuthUserDto;
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
        checkIfUserAlreadyExists(userCreateDto);
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
                    existingUser.setPassword(userDto.getPassword());
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

    public AuthUserDto getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toAuthUserDto)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
    }

    private void checkIfUserAlreadyExists(UserCreateDto userCreateDto) {
        if(userRepository.findByUsername(userCreateDto.getUsername()).isPresent()){
            throw new RuntimeException(String.format("Użytkownik z loginem '%s' już istnieje.", userCreateDto.getUsername()));
        }
    }
}
