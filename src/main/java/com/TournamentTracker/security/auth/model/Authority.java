package com.TournamentTracker.security.auth.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_TOURNAMENT_OWNER,
    ROLE_TEAM_OWNER,
    ROLE_USER;

    public String getAuthority() {
        return name();
    }
}
