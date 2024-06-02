package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.security.auth.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class GameServiceImpl implements GameService{
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final AuthService authService;

    public List<GameDto> getAll() {
        return gameMapper.toDtoList(gameRepository.findAll());
    }

    public GameDto getById(Long id) {
        return gameRepository.findById(id)
                .map(gameMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + "not found"));
    }

    public void deleteById(Long id) {
        Long userId = authService.getCurrentUser().getId();
        GameDto gameDto = getById(id);
        if((userId.equals(gameDto.getTournament().getId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            gameRepository.deleteById(id);
        } else {
            throw new RuntimeException("You are not authorized to delete this game");
        }
    }

    public List<GameDto> getGamesByTournamentId(Long tournamentId) {
        return gameMapper.toDtoList(gameRepository.findByTournament_Id(tournamentId));
    }

    public List<GameTournamentDto> getOnlyGames() {
        return gameMapper.toGameTournamentDtoList(gameRepository.findAll());
    }

    public String getFinalScore(Long id) {
        GameDto gameDto = getById(id);
        return gameDto.getHomeTeamScore() + " : " + gameDto.getGuestTeamScore();
    }

    public String setFinalScore(Long id, Long homeTeamScore, Long guestTeamScore) {
        GameDto gameDto = getById(id);
        if((authService.getCurrentUser().getId().equals(gameDto.getTournament().getId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            gameDto.setHomeTeamScore(homeTeamScore);
            gameDto.setGuestTeamScore(guestTeamScore);
            gameDto.setFinalScore(homeTeamScore + " : " + guestTeamScore);
            gameRepository.save(gameMapper.toEntity(gameDto));
            return "Final score set successfully";
        } else {
            throw new RuntimeException("You are not authorized to set final score for this game");
        }
    }

}
