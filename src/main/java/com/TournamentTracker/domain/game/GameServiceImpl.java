package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.util.handler.exception.NotAuthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.TournamentTracker.util.ExceptionMessages.GAME_NOT_FOUND;
import static com.TournamentTracker.util.ExceptionMessages.NOT_AUTHORIZED_GAME_TOURNAMENT;

@Service
@RequiredArgsConstructor
@Transactional
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
                .orElseThrow(() -> new EntityNotFoundException(String.format(GAME_NOT_FOUND, id)));
    }

    public void deleteById(Long id) {
        Long userId = authService.getCurrentUser().getId();
        GameDto gameDto = getById(id);
        if((userId.equals(gameDto.getTournament().getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            gameRepository.deleteById(id);
        } else {
            throw new NotAuthorizedException(NOT_AUTHORIZED_GAME_TOURNAMENT);
        }
    }

    public List<GameDto> getGamesByTournamentId(Long tournamentId) {
        return gameMapper.toDtoList(gameRepository.findByTournament_Id(tournamentId));
    }

    public List<GameTournamentDto> getOnlyGames() {
        return gameMapper.toGameTournamentDtoList(gameRepository.findAll());
    }

    public String setFinalScore(Long id, Long homeTeamScore, Long guestTeamScore) {
        GameDto gameDto = getById(id);
        if((authService.getCurrentUser().getId().equals(gameDto.getTournament().getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            gameDto.setHomeTeamScore(homeTeamScore);
            gameDto.setGuestTeamScore(guestTeamScore);
            gameDto.setFinalScore(homeTeamScore + " : " + guestTeamScore);
            gameRepository.save(gameMapper.toEntity(gameDto));
            return String.format("Wynik dla meczu o id %s został ustawiony %s : %s", id, homeTeamScore, guestTeamScore);
        } else {
            throw new NotAuthorizedException(NOT_AUTHORIZED_GAME_TOURNAMENT);
        }
    }

}
