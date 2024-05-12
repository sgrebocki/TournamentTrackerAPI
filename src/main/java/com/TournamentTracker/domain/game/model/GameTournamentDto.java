package com.TournamentTracker.domain.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class GameTournamentDto {
    Long id;
    Date gameTime;
    String score;
    @JsonIgnore
    Long tournamentId;
}
