package com.TournamentTracker.security.auth;

import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.UserDto;
import com.TournamentTracker.security.auth.model.Authority;

import java.util.Set;

public interface AuthService {
    AuthUserDto getCurrentUser();
    Set<Authority> getCurrentUserAuthorities();
    boolean hasAdminRole();
    boolean hasTeamOwnerRole();
    boolean hasTournamentOwnerRole();
}
