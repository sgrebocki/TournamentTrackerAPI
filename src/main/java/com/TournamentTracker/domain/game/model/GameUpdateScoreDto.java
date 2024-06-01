package com.TournamentTracker.domain.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GameUpdateScoreDto {
    Long id;
    Long homeTeamScore;
    Long guestTeamScore;
}
