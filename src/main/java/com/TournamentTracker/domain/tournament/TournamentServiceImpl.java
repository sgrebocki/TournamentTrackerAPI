package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.game.GameService;
import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.sport.SportMapper;
import com.TournamentTracker.domain.sport.SportService;
import com.TournamentTracker.domain.team.TeamMapper;
import com.TournamentTracker.domain.team.TeamService;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.Tournament;
import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class TournamentServiceImpl implements TournamentService{
    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;
    private final SportService sportService;
    private final SportMapper sportMapper;
    private final GameService gameService;
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public List<TournamentDto> getAll() {
        List<TournamentDto> tournaments = tournamentMapper.toDtoList(tournamentRepository.findAll());

        for (TournamentDto tournamentDto : tournaments) {
            tournamentDto.setGamesList(getMappedGames(tournamentDto));
            tournamentDto.setTeamsList(getMappedTeams(tournamentDto));
        }

        return tournaments;
    }

    public TournamentDto getById(Long id) {
        return tournamentRepository.findById(id)
                .map(tournament -> {
                    TournamentDto tournamentDto = tournamentMapper.toDto(tournament);

                    tournamentDto.setGamesList(getMappedGames(tournamentDto));
                    tournamentDto.setTeamsList(getMappedTeams(tournamentDto));

                    return tournamentDto;
                }).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + id + " not found"));
    }

    public TournamentDto create(TournamentCreateDto tournamentDto) {
        return tournamentMapper.toDto(tournamentRepository.save(tournamentMapper.toEntity(tournamentDto)));
    }

    public TournamentDto update(TournamentCreateDto tournamentDto, Long id) {
        return tournamentRepository.findById(id)
                .map(editTournament -> {
                    editTournament.setId(id);
                    editTournament.setName(tournamentDto.getName());
                    editTournament.setDateTime(tournamentDto.getDateTime());
                    editTournament.setLocation(tournamentDto.getLocation());
                    editTournament.setStreet(tournamentDto.getStreet());
                    editTournament.setSport(sportMapper.toEntity(sportService.getById(tournamentDto.getSportId())));
                    return tournamentMapper.toDto(tournamentRepository.save(tournamentMapper.toEntity(tournamentDto)));
                }).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + id + " not found"));
    }

    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    private List<GameTournamentDto> getMappedGames(TournamentDto tournamentDto) {
        return gameService.getOnlyGames()
                .stream()
                .filter(game -> game.getTournamentId() != null && game.getTournamentId().equals(tournamentDto.getId()))
                .collect(Collectors.toList());
    }

    private List<TeamTournamentDto> getMappedTeams(TournamentDto tournamentDto) {
        return teamService.getOnlyTeams()
                .stream()
                .filter(team -> team.getTournamentId() != null && team.getTournamentId().equals(tournamentDto.getId()))
                .collect(Collectors.toList());
    }
}
