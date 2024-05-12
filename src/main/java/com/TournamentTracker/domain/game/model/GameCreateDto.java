package com.TournamentTracker.domain.game.model;

import lombok.Data;

import java.util.Date;


@Data
public class GameCreateDto {
    Date gameTime;
    Long tournamentId;
}
