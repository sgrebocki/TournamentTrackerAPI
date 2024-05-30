package com.TournamentTracker.security.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ADMIN,
    TOURNAMENT_CREATOR,
    TEAM_OWNER,
    PLAYER;

    public String getAuthority() {
        return name();
    }
}
