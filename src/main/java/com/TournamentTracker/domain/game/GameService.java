package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;

import java.util.List;

public interface GameService {
    List<GameDto> getAll();
    GameDto getById(Long id);
    GameDto create(GameCreateDto gameDto);
    GameDto update(GameDto gameDto, Long id);
    void deleteById(Long id);
    List<GameTournamentDto> getOnlyGames();
}
