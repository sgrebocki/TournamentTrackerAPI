package com.TournamentTracker.domain.user;

import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AccountController", description = "Current user account management")
@RestController
@AllArgsConstructor
@RequestMapping("/api/account")
class AccountController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<AuthUserDto> getAccountParameters(){
        AuthUserDto user = userService.getAccountParameters();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.changePassword(oldPassword, newPassword));
    }

    @GetMapping("/changeUsername")
    public ResponseEntity<UserDto> changeUsername(@RequestParam String newUsername) {
        return ResponseEntity.ok(userService.changeUsername(newUsername));
    }
}
