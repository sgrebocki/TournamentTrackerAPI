package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class GameServiceImpl implements GameService{
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public List<GameDto> getAll() {
        return gameMapper.toDtoList(gameRepository.findAll());
    }

    public GameDto getById(Long id) {
        return gameRepository.findById(id)
                .map(gameMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + "not found"));
    }

    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    public List<GameTournamentDto> getOnlyGames() {
        return gameMapper.toGameTournamentDtoList(gameRepository.findAll());
    }

}
