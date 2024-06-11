package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.Game;
import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.team.TeamMapper;
import com.TournamentTracker.domain.team.TeamService;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.tournament.TournamentMapper;
import com.TournamentTracker.domain.tournament.TournamentService;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.util.handler.exception.IllegalAccessException;
import com.TournamentTracker.util.handler.exception.NotAuthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.TournamentTracker.util.ExceptionMessages.GAME_NOT_FOUND;
import static com.TournamentTracker.util.ExceptionMessages.NOT_AUTHORIZED_GAME_TOURNAMENT;

@Service
@RequiredArgsConstructor
@Transactional
class GameTeamServiceImpl implements GameTeamService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;
    private final AuthService authService;

    public GameDto create(GameCreateDto gameDto) {
        TournamentDto tournamentDto = tournamentService.getById(gameDto.getTournamentId());
        if((authService.getCurrentUser().getId().equals(tournamentDto.getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            Game game = gameMapper.toEntity(gameDto);

            TeamDto homeTeam = teamService.getById(gameDto.getHomeTeamId());
            TeamDto guestTeam = teamService.getById(gameDto.getGuestTeamId());

            game.setHomeTeam(teamMapper.toEntity(homeTeam));
            game.setGuestTeam(teamMapper.toEntity(guestTeam));
            game.setTournament(tournamentMapper.toEntity(tournamentDto));

            return gameMapper.toDto(gameRepository.save(game));
        } else {
            throw new IllegalAccessException(NOT_AUTHORIZED_GAME_TOURNAMENT);
        }
    }

    public GameDto update(GameDto gameDto, Long id) {
        if((authService.getCurrentUser().getId().equals(gameDto.getTournament().getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()){
        return gameRepository.findById(id)
                .map(editGame -> {
                    editGame.setGameTime(gameDto.getGameTime());
                    editGame.setHomeTeam(teamMapper.toEntity(gameDto.getHomeTeam()));
                    editGame.setGuestTeam(teamMapper.toEntity(gameDto.getGuestTeam()));
                    editGame.setTournament(tournamentMapper.toEntity(gameDto.getTournament()));
                    return gameMapper.toDto(gameRepository.save(editGame));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(GAME_NOT_FOUND, id)));
        } else {
            throw new NotAuthorizedException(NOT_AUTHORIZED_GAME_TOURNAMENT);
        }
    }
}
