package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TeamTournamentController", description = "For signing up and signing out from tournaments")
@RestController
@AllArgsConstructor
@RequestMapping("/teams/tournament")
class TeamTournamentController {
    private final TeamTournamentService teamTournamentService;

    @PutMapping("/signUp/{id}")
    public ResponseEntity<TeamTournamentDto> signUpForTournament(@RequestBody TeamDto teamDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamTournamentService.signUpForTournament(teamDto, id));
    }

    @PutMapping("/signOut/{id}")
    public ResponseEntity<TeamTournamentDto> signOutFromTournament(@RequestBody TeamDto teamDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamTournamentService.signOutFromTournament(teamDto, id));
    }
}
