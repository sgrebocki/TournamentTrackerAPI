package com.TournamentTracker.domain.user.model;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.security.auth.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class AuthUserDto {
    Long id;
    String username;
    String firstName;
    String lastName;
    TeamDto team;
    Set<Authority> authorities;
}
