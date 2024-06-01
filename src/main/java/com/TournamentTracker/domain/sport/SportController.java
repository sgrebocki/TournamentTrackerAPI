package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.model.SportCreateDto;
import com.TournamentTracker.domain.sport.model.SportDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SportController", description = "For POST, PUT, DELETE required role: ADMIN")
@RestController
@AllArgsConstructor
@RequestMapping("/api/sports")
class SportController {
    private final SportService sportService;

    @GetMapping
    public ResponseEntity<List<SportDto>> getAllSports(){
        List<SportDto> sportList = sportService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(sportList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable Long id){
        SportDto sport = sportService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(sport);
    }

    @PostMapping()
    public ResponseEntity<?> addSport(@RequestBody SportCreateDto sportDto){
        try {
            SportDto sportCreateDto = sportService.create(sportDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sportCreateDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportDto> editSport(@RequestBody SportDto sportDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(sportService.update(sportDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id){
        sportService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
