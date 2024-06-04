package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.TournamentMapper;
import com.TournamentTracker.domain.tournament.TournamentService;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.security.auth.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TeamTournamentServiceImpl implements TeamTournamentService {
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final TeamTournamentMapper teamTournamentMapper;
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;
    private final AuthService authService;

    public TeamTournamentDto signUpForTournament(Long tournamentId) {
        AuthUserDto currentUser = authService.getCurrentUser();
        TeamDto team = teamService.getOwnedTeam(currentUser.getId());

        if(authService.hasTeamOwnerRole() || authService.hasAdminRole()) {
            return teamRepository.findById(team.getId())
                    .map(editTeam -> {
                        editTeam.setTournament(tournamentMapper.toEntity(tournamentService.getById(tournamentId)));
                        return teamTournamentMapper.toDto(teamRepository.save(editTeam));
                    }).orElseThrow(() -> new EntityNotFoundException("Team with id " + team.getId() + " not found"));
        } else {
            throw new RuntimeException("You are not owner of any team");
        }
    }

    public TeamTournamentDto signOutFromTournament(Long tournamentId) {
        AuthUserDto currentUser = authService.getCurrentUser();
        TeamDto team = teamService.getOwnedTeam(currentUser.getId());

        if(authService.hasTeamOwnerRole() || authService.hasAdminRole()) {
            return teamRepository.findById(team.getId())
                    .map(editTeam -> {
                        editTeam.setTournament(null);
                        return teamTournamentMapper.toDto(teamRepository.save(editTeam));
                    }).orElseThrow(() -> new EntityNotFoundException("Team with id " + team.getId() + " not found"));
        } else {
            throw new RuntimeException("You are not owner of any team");
        }
    }
}
