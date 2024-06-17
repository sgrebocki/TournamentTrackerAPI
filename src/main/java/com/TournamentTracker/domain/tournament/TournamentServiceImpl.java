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
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import com.TournamentTracker.util.handler.exception.NotAuthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.TournamentTracker.util.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Transactional
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

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if(!(authentication instanceof AnonymousAuthenticationToken)) {
                        AuthUserDto currentUser = authService.getCurrentUser();
                        tournamentDto.setCanUpdateOrDelete(currentUser.getId().equals(tournament.getOwnerId()) || authService.hasAdminRole());
                    }

                    return tournamentDto;
                }).orElseThrow(() -> new EntityNotFoundException(String.format(TOURNAMENT_NOT_FOUND, id)));
    }

    public TournamentDto create(TournamentCreateDto tournamentDto) {
        AuthUserDto currentUser = authService.getCurrentUser();
        if (isUserAlreadyOwner(currentUser.getId())) {
            throw new RuntimeException(ALREADY_OWNER_OF_TOURNAMENT);
        }
        Tournament tournament = tournamentMapper.toEntity(tournamentDto);
        tournament.setOwnerId(currentUser.getId());
        Tournament savedTournament = tournamentRepository.save(tournament);

        userService.addAuthority(currentUser.getId(), Authority.ROLE_TOURNAMENT_OWNER);
        return tournamentMapper.toDto(savedTournament);
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
                    }).orElseThrow(() -> new EntityNotFoundException(String.format(TOURNAMENT_NOT_FOUND, id)));
        } else {
            throw new NotAuthorizedException(String.format(NOT_AUTHORIZED_TOURNAMENT));
        }
    }

    public void deleteById(Long id) {
        AuthUserDto currentUser = authService.getCurrentUser();
        TournamentDto tournamentDto = getById(id);
        if((currentUser.getId().equals(tournamentDto.getOwnerId()) && authService.hasTournamentOwnerRole()) || authService.hasAdminRole()) {
            userService.removeAuthority(currentUser.getId(), Authority.ROLE_TOURNAMENT_OWNER);
            tournamentRepository.deleteById(id);
        } else {
            throw new NotAuthorizedException(NOT_AUTHORIZED_TOURNAMENT);
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
        return !tournamentRepository.findAllByOwnerId(userId).isEmpty();
    }
}
