package com.TournamentTracker.domain.user;

import com.TournamentTracker.security.auth.model.Authority;
import com.TournamentTracker.domain.user.model.UserCreateDto;
import com.TournamentTracker.domain.user.model.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "UserController", description = "For POST, PUT, DELETE required role: ADMIN")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userList = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UserCreateDto userCreateDto){
        try {
            UserDto userDto = userService.create(userCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userCreateDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userCreateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/authorities")
    public ResponseEntity<UserDto> addAuthority(@PathVariable Long id, @RequestBody Authority authority) {
        UserDto userDto = userService.addAuthority(id, authority);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/{id}/authorities")
    public ResponseEntity<UserDto> removeAuthority(@PathVariable Long id, @RequestBody Authority authority) {
        UserDto userDto = userService.removeAuthority(id, authority);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/{id}/authorities")
    public ResponseEntity<UserDto> updateAuthorities(@PathVariable Long id, @RequestBody Set<Authority> authorities) {
        UserDto userDto = userService.updateAuthorities(id, authorities);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> ids) {
        userService.deleteMany(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
