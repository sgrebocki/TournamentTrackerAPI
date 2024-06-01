package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;

public interface GameTeamService {
    GameDto create(GameCreateDto gameDto);
    GameDto update(GameDto gameDto, Long id);
}
