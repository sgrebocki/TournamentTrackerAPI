package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.game.GameService;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.sport.SportMapper;
import com.TournamentTracker.domain.sport.SportService;
import com.TournamentTracker.domain.team.TeamService;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.Tournament;
import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import com.TournamentTracker.domain.user.UserService;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final AuthService authService;
    private final UserService userService;

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

                    Long currentUserId = authService.getCurrentUser().getId();
                    tournamentDto.setCanUpdateOrDelete(currentUserId.equals(tournament.getOwnerId()));

                    return tournamentDto;
                }).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + id + " not found"));
    }

    public TournamentDto create(TournamentCreateDto tournamentDto) {
        Long userId = authService.getCurrentUser().getId();
        if (isUserAlreadyOwner(userId)) {
            throw new RuntimeException("You are already an owner of a tournament");
        }
        Tournament tournament = tournamentMapper.toEntity(tournamentDto);
        tournament.setOwnerId(userId);
        userService.addAuthority(userId, Authority.ROLE_TOURNAMENT_OWNER);
        return tournamentMapper.toDto(tournamentRepository.save(tournament));
    }

    public TournamentDto update(TournamentCreateDto tournamentDto, Long id) {
        if((authService.getCurrentUser().getId().equals(tournamentDto.getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            return tournamentRepository.findById(id)
                    .map(editTournament -> {
                        editTournament.setName(tournamentDto.getName());
                        editTournament.setDateTime(tournamentDto.getDateTime());
                        editTournament.setLocation(tournamentDto.getLocation());
                        editTournament.setStreet(tournamentDto.getStreet());
                        editTournament.setSport(sportMapper.toEntity(sportService.getById(tournamentDto.getSportId())));
                        return tournamentMapper.toDto(tournamentRepository.save(editTournament));
                    }).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + id + " not found"));
        } else {
            throw new RuntimeException("You are not authorized to update this tournament");
        }
    }

    public void deleteById(Long id) {
        Long userId = authService.getCurrentUser().getId();
        TournamentDto tournamentDto = getById(id);
        if((userId.equals(tournamentDto.getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            userService.removeAuthority(userId, Authority.ROLE_TOURNAMENT_OWNER);
            tournamentRepository.deleteById(id);
        } else {
            throw new RuntimeException("You are not authorized to delete this tournament");
        }
    }

    public List<GameTournamentDto> getMappedGames(TournamentDto tournamentDto) {
        return gameService.getOnlyGames()
                .stream()
                .filter(game -> game.getTournamentId() != null && game.getTournamentId().equals(tournamentDto.getId()))
                .collect(Collectors.toList());
    }

    public List<TeamTournamentDto> getMappedTeams(TournamentDto tournamentDto) {
        return teamService.getOnlyTeams()
                .stream()
                .filter(team -> team.getTournamentId() != null && team.getTournamentId().equals(tournamentDto.getId()))
                .collect(Collectors.toList());
    }

    public boolean isUserAlreadyOwner(Long userId) {
        return tournamentRepository.findByOwnerId(userId).isPresent();
    }
}
