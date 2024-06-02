package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameDto {
    Long id;
    LocalDateTime gameTime;
    TeamTournamentDto homeTeam;
    Long homeTeamScore;
    TeamTournamentDto guestTeam;
    Long guestTeamScore;
    String finalScore;
    TournamentTeamDto tournament;
}
