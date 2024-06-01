package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;

import java.util.List;

public interface GameService {
    List<GameDto> getAll();
    GameDto getById(Long id);
    void deleteById(Long id);
    List<GameTournamentDto> getOnlyGames();
}
