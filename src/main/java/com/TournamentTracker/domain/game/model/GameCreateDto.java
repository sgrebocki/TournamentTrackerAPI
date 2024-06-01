package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import lombok.Data;

import java.util.Date;


@Data
public class GameCreateDto {
    Date gameTime;
    Long tournamentId;
    Long homeTeamId;
    Long guestTeamId;
}
