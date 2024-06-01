package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.TournamentMapper;
import com.TournamentTracker.domain.tournament.TournamentService;
import com.TournamentTracker.security.auth.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TeamTournamentServiceImpl implements TeamTournamentService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TeamTournamentMapper teamTournamentMapper;
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;
    private final AuthService authService;

    public TeamTournamentDto signUpForTournament(TeamDto teamDto, Long id) {
        if((authService.getCurrentUser().getId().equals(teamDto.getOwnerId()) && authService.hasTeamOwnerRole()) || authService.hasAdminRole()) {
            return teamRepository.findById(id)
                    .map(editTeam -> {
                        editTeam.setTournament(tournamentMapper.toEntity(tournamentService.getById(teamDto.getTournament().getId())));
                        return teamTournamentMapper.toDto(teamRepository.save(teamMapper.toEntity(teamDto)));
                    }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
        } else {
            throw new RuntimeException("You are not authorized to sign up this team for tournament");
        }
    }

    public TeamTournamentDto signOutFromTournament(TeamDto teamDto, Long id) {
        if((authService.getCurrentUser().getId().equals(teamDto.getOwnerId()) && authService.hasTeamOwnerRole()) || authService.hasAdminRole()) {
            return teamRepository.findById(id)
                    .map(editTeam -> {
                        editTeam.setTournament(null);
                        return teamTournamentMapper.toDto(teamRepository.save(teamMapper.toEntity(teamDto)));
                    }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
        } else {
            throw new RuntimeException("You are not authorized to sign out this team from tournament");
        }
    }
}
