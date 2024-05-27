package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class TeamTournamentController {
    private final TeamTournamentService teamTournamentService;

    @PutMapping("/{id}")
    public ResponseEntity<TeamTournamentDto> editTeam(@RequestBody TeamDto teamDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamTournamentService.update(teamDto, id));
    }
}
