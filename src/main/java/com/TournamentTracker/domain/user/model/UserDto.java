package com.TournamentTracker.domain.user.model;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    Long id;
    @Email(message = "Nazwa użytkownika musi być poprawnie sformatowanym adresem e-mail")
    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    String username;
    String firstName;
    String lastName;
    Long teamId;
    Set<Authority> authorities;
}
