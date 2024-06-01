package com.TournamentTracker.security.auth;

import com.TournamentTracker.domain.user.model.UserDto;
import com.TournamentTracker.security.auth.model.Authority;

import java.util.Set;

public interface AuthService {
    public UserDto getCurrentUser();
    public Set<Authority> getCurrentUserAuthorities();
    public boolean hasPlayerRole();
    public boolean hasTeamOwnerRole();
    public boolean hasTournamentOwnerRole();
}
