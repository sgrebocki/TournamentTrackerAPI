package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.user.UserService;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final AuthService authService;
    private final UserService userService;

    public List<TeamDto> getAll() {
        return teamMapper.toDtoList(teamRepository.findAll());
    }

    public TeamDto getById(Long id) {
        return teamRepository.findById(id)
            .map(teamMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }

    public TeamDto create(TeamCreateDto teamCreateDto) {
        Long userId = authService.getCurrentUser().getId();
        if (isUserAlreadyOwner(userId)) {
            throw new RuntimeException("You are already an owner of a team");
        }
        Team team = teamMapper.toEntity(teamCreateDto);
        team.setOwnerId(userId);
        userService.addAuthority(userId, Authority.ROLE_TEAM_OWNER);
        return teamMapper.toDto(teamRepository.save(team));
    }

    public TeamDto update(TeamDto teamDto, Long id) {
        if((authService.getCurrentUser().getId().equals(teamDto.getOwnerId()) && authService.hasTeamOwnerRole()) || authService.hasAdminRole()) {
            return teamRepository.findById(id)
                    .map(editTeam -> {
                        editTeam.setId(id);
                        editTeam.setName(teamDto.getName());
                        return teamMapper.toDto(teamRepository.save(editTeam));
                    }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
        } else {
            throw new RuntimeException("You are not authorized to update this team");
        }
    }

    public void deleteById(Long id) {
        Long userId = authService.getCurrentUser().getId();
        TeamDto teamDto = getById(id);
        if((userId.equals(teamDto.getOwnerId()) && authService.hasTeamOwnerRole()) || authService.hasAdminRole()) {
            teamRepository.deleteById(id);
        } else {
            throw new RuntimeException("You are not authorized to delete this team");
        }
    }

    public List<TeamTournamentDto> getOnlyTeams() {
        return teamMapper.toTeamTournamentDtoList(teamRepository.findAll());
    }

    public boolean isUserAlreadyOwner(Long userId) {
        return teamRepository.findByOwnerId(userId).isPresent();
    }
}
