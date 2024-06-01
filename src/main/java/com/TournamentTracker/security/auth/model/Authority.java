package com.TournamentTracker.security.auth.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ADMIN,
    TOURNAMENT_OWNER,
    TEAM_OWNER,
    PLAYER;

    public String getAuthority() {
        return name();
    }
}
