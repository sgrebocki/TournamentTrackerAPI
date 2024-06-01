package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tournaments")
class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<TournamentDto>> getAllTournaments(){
        List<TournamentDto> tournamentList = tournamentService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(tournamentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDto> getTournamentById(@PathVariable Long id){
        TournamentDto tournament = tournamentService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tournament);
    }

    @PostMapping()
    public ResponseEntity<TournamentDto> addTournament(@RequestBody TournamentCreateDto tournamentDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tournamentService.create(tournamentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentDto> editTournament(@RequestBody TournamentCreateDto tournamentDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tournamentService.update(tournamentDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id){
        tournamentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameTournamentDto>> getTournamentGames(@PathVariable Long id){
        List<GameTournamentDto> games = tournamentService.getMappedGames(tournamentService.getById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(games);
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<List<TeamTournamentDto>> getTournamentTeams(@PathVariable Long id){
        List<TeamTournamentDto> teams = tournamentService.getMappedTeams(tournamentService.getById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(teams);
    }
}
