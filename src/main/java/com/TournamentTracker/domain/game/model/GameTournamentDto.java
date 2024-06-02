package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameTournamentDto {
    Long id;
    LocalDateTime gameTime;
    TeamTournamentDto homeTeam;
    Long homeTeamScore;
    TeamTournamentDto guestTeam;
    Long guestTeamScore;
    String finalScore;
    @JsonIgnore
    Long tournamentId;
}
