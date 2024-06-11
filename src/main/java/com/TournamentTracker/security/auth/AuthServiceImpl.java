package com.TournamentTracker.security.auth;

import com.TournamentTracker.domain.user.UserMapper;
import com.TournamentTracker.domain.user.UserRepository;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.User;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.TournamentTracker.util.ExceptionMessages.USER_NOT_AUTHENTICATED;
import static com.TournamentTracker.util.ExceptionMessages.USER_NOT_FOUND_BY_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(USER_NOT_AUTHENTICATED);
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toAuthUserDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));
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
