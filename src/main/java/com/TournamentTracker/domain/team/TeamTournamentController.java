package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TeamTournamentController", description = "For signing up and signing out from tournaments")
@RestController
@AllArgsConstructor
@RequestMapping("api/teams/tournament")
class TeamTournamentController {
    private final TeamTournamentService teamTournamentService;

    @PutMapping("/signUp/{tournamentId}")
    public ResponseEntity<TeamTournamentDto> signUpForTournament(@PathVariable Long tournamentId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamTournamentService.signUpForTournament(tournamentId));
    }

    @PutMapping("/signOut/{tournamentId}")
    public ResponseEntity<TeamTournamentDto> signOutFromTournament(@PathVariable Long tournamentId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamTournamentService.signOutFromTournament(tournamentId));
    }
}
