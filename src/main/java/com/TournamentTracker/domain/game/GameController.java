package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/games")
class GameController {
    private final GameService gameService;
    private final GameTeamService gameTeamService;

    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames(){
        List<GameDto> gameList = gameService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id){
        GameDto game = gameService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(game);
    }

    @PostMapping()
    public ResponseEntity<?> addGame(@RequestBody GameCreateDto gameCreateDto){
        try {
            GameDto gameDto = gameTeamService.create(gameCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(gameDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDto> editGame(@RequestBody GameDto gameDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameTeamService.update(gameDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id){
        gameService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
