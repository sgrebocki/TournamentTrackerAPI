package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import lombok.Data;

import java.util.Date;

@Data
public class GameDto {
    Long id;
    Date gameTime;
    TeamDto homeTeam;
    Long homeTeamScore;
    TeamDto guestTeam;
    Long guestTeamScore;
    String finalScore;
    TournamentTeamDto tournament;
}
