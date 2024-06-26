package com.TournamentTracker.domain.user;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.security.auth.model.Authority;
import com.TournamentTracker.domain.user.model.UserCreateDto;
import com.TournamentTracker.domain.user.model.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto create(UserCreateDto userDto);
    UserDto update(UserDto userDto, Long id);
    void deleteById(Long id);
    void deleteMany(List<Long> ids);
    UserDto addAuthority(Long userId, Authority authority);
    UserDto removeAuthority(Long userId, Authority authority);
    UserDto updateAuthorities(Long userId, Set<Authority> authorities);
    AuthUserDto getAccountParameters();
    UserDto changePassword(String oldPassword, String newPassword);
    UserDto changeUsername(String newUsername);
    void setTeamForUser(Long userId, Team team);
}
