package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "GameController", description = "For POST, PUT, DELETE required role: TOURNAMENT_CREATOR or ADMIN")
@RestController
@AllArgsConstructor
@RequestMapping("/api/games")
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

    @PutMapping("/{id}/setFinalScore")
    public ResponseEntity<String> setFinalScore(@PathVariable Long id, @RequestParam Long homeTeamScore, @RequestParam Long guestTeamScore){
        return ResponseEntity.status(HttpStatus.OK)
                .body(gameService.setFinalScore(id, homeTeamScore, guestTeamScore));
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<List<GameDto>> getTournamentGames(@PathVariable Long tournamentId){
        List<GameDto> games = gameService.getGamesByTournamentId(tournamentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(games);
    }

}
