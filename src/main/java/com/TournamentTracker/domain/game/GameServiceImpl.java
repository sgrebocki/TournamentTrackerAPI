package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.tournament.TournamentService;
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

    public GameDto create(GameCreateDto gameDto) {
        return gameMapper.toDto(gameRepository.save(gameMapper.toEntity(gameDto)));
    }

    public GameDto update(GameDto gameDto, Long id) {
        return gameRepository.findById(id)
                .map(editGame ->{
                    editGame.setId(id);
                    editGame.setGameTime(gameDto.getGameTime());
                    editGame.setScore(gameDto.getScore());
                    editGame.setTournament(gameDto.getTournament());
                    return gameMapper.toDto(gameRepository.save(gameMapper.toEntity(gameDto)));
                }).orElseThrow(() -> new EntityNotFoundException("Game with id " + id + "not found"));
    }

    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    public List<GameTournamentDto> getOnlyGames() {
        return gameMapper.toGameTournamentDtoList(gameRepository.findAll());
    }

}
