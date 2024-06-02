package com.TournamentTracker.domain.game.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class GameCreateDto {
    LocalDateTime gameTime;
    Long tournamentId;
    Long homeTeamId;
    Long guestTeamId;
}
