package com.TournamentTracker.domain.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @Email(message = "Nazwa użytkownika musi być poprawnie sformatowanym adresem e-mail")
    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    String username;
    @NotBlank
    @Size(min = 8, message = "Hasło musi zawierać co najmniej 8 znaków")
    String password;
    String firstName;
    String lastName;
}
