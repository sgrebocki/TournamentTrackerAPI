package com.TournamentTracker.domain.user.model;
import com.TournamentTracker.security.auth.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    Long teamId;
    Set<Authority> authorities;
}
