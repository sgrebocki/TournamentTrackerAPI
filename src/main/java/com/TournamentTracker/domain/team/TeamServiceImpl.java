package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.user.UserService;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .map(team -> {
                    TeamDto teamDto = teamMapper.toDto(team);

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (!(authentication instanceof AnonymousAuthenticationToken)) {
                        Long currentUserId = authService.getCurrentUser().getId();
                        teamDto.setCanUpdateOrDelete(currentUserId.equals(team.getOwnerId()));
                    }

                    return teamDto;
                }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }

    public TeamDto create(TeamCreateDto teamCreateDto) {
        AuthUserDto currentUser = authService.getCurrentUser();
        if (isUserAlreadyOwner(currentUser.getId()) || isUserInTeam()) {
            throw new RuntimeException("You are already an owner or member of a team");
        }
        userService.addAuthority(currentUser.getId(), Authority.ROLE_TEAM_OWNER);
        Team team = teamMapper.toEntity(teamCreateDto);
        team.setOwnerId(currentUser.getId());
        Team savedTeam = teamRepository.save(team);

        userService.setTeamForUser(currentUser.getId(), savedTeam);
        return teamMapper.toDto(savedTeam);
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
        AuthUserDto currentUser = authService.getCurrentUser();
        TeamDto teamDto = getById(id);
        if((currentUser.getId().equals(teamDto.getOwnerId()) && authService.hasTeamOwnerRole()) || authService.hasAdminRole()) {
            userService.removeAuthority(currentUser.getId(), Authority.ROLE_TEAM_OWNER);
            teamRepository.deleteById(id);
        } else {
            throw new RuntimeException("You are not authorized to delete this team");
        }
    }

    public List<TeamTournamentDto> getOnlyTeams() {
        return teamMapper.toTeamTournamentDtoList(teamRepository.findAll());
    }

    public TeamDto getOwnedTeam(Long userId) {
        return teamRepository.findByOwnerId(userId)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not own any team"));
    }

    public boolean isUserAlreadyOwner(Long userId) {
        return !teamRepository.findAllByOwnerId(userId).isEmpty();
    }

    public boolean isUserInTeam(){
        return authService.getCurrentUser().getTeam() != null;
    }
}
