package com.TournamentTracker.security.auth;

import com.TournamentTracker.domain.user.UserMapper;
import com.TournamentTracker.domain.user.UserService;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.User;
import com.TournamentTracker.domain.user.model.UserDto;
import com.TournamentTracker.security.auth.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserMapper userMapper;
    public AuthUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
        String username = authentication.getName();
        return userService.getByUsername(username);
    }

    public Set<Authority> getCurrentUserAuthorities() {
        User user = userMapper.toEntity(getCurrentUser());
        return user.getAuthorities();
    }

    public boolean hasAdminRole() {
        Set<Authority> authorities = getCurrentUserAuthorities();
        return authorities.contains(Authority.ROLE_ADMIN);
    }

    public boolean hasTeamOwnerRole() {
        Set<Authority> authorities = getCurrentUserAuthorities();
        return authorities.contains(Authority.ROLE_TEAM_OWNER);
    }

    public boolean hasTournamentOwnerRole() {
        Set<Authority> authorities = getCurrentUserAuthorities();
        return authorities.contains(Authority.ROLE_TOURNAMENT_OWNER);
    }
}
