package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.tournament.model.Tournament;
import lombok.Data;

import java.util.Date;

@Data
public class GameDto {
    Long id;
    Date gameTime;
    String score;
    Tournament tournament;
}
