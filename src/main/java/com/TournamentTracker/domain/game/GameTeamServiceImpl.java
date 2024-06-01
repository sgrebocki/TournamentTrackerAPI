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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GameTeamServiceImpl implements GameTeamService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;

    @Transactional
    public GameDto create(GameCreateDto gameDto) {
        Game game = gameMapper.toEntity(gameDto);

        TeamDto homeTeam = teamService.getById(gameDto.getHomeTeamId());
        TeamDto guestTeam = teamService.getById(gameDto.getGuestTeamId());
        TournamentDto tournament = tournamentService.getById(gameDto.getTournamentId());

        game.setHomeTeam(teamMapper.toEntity(homeTeam));
        game.setGuestTeam(teamMapper.toEntity(guestTeam));
        game.setTournament(tournamentMapper.toEntity(tournament));

        return gameMapper.toDto(gameRepository.save(game));
    }

    @Transactional
    public GameDto update(GameDto gameDto, Long id) {
        return gameRepository.findById(id)
                .map(editGame -> {
                    editGame.setGameTime(gameDto.getGameTime());
                    editGame.setHomeTeam(teamMapper.toEntity(gameDto.getHomeTeam()));
                    editGame.setHomeTeamScore(gameDto.getHomeTeamScore());
                    editGame.setGuestTeam(teamMapper.toEntity(gameDto.getGuestTeam()));
                    editGame.setGuestTeamScore(gameDto.getGuestTeamScore());
                    editGame.setTournament(tournamentMapper.toEntity(gameDto.getTournament()));
                    return gameMapper.toDto(gameRepository.save(editGame));
                }).orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));
    }
}
