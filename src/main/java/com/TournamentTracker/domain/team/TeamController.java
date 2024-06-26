package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TeamController", description = "For POST, PUT, DELETE required role: TEAM_CREATOR or ADMIN")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams(){
        List<TeamDto> teamList = teamService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id){
        TeamDto teamDto = teamService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamDto);
    }

    @PostMapping()
    public ResponseEntity<?> addTeam(@RequestBody TeamCreateDto teamCreateDto){
        try {
            TeamDto teamDto = teamService.create(teamCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(teamDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> editTeam(@RequestBody TeamDto teamDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.update(teamDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{teamId}/addUser/{userId}")
    public ResponseEntity<Void> addUserToTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.addUserToTeam(teamId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{teamId}/quit")
    public ResponseEntity<Void> quitFromTeam(@PathVariable Long teamId) {
        teamService.quitFromTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}