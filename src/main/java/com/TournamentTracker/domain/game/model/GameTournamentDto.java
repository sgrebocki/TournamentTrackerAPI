package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class GameTournamentDto {
    Long id;
    Date gameTime;
    TeamDto homeTeam;
    Long homeTeamScore;
    TeamDto guestTeam;
    Long guestTeamScore;
    String finalScore;
    @JsonIgnore
    Long tournamentId;
}
