package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.user.UserMapper;
import com.TournamentTracker.domain.user.UserRepository;
import com.TournamentTracker.domain.user.UserService;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.User;
import com.TournamentTracker.security.auth.AuthService;
import com.TournamentTracker.security.auth.model.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<TeamDto> getAll() {
        return teamRepository.findAll().stream()
                .map(team -> {
                    TeamDto teamDto = teamMapper.toDto(team);
                    teamDto.setUsers(userMapper.toDtoList(team.getUsers()));
                    return teamDto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public TeamDto getById(Long id) {
        return teamRepository.findById(id)
                .map(team -> {
                    TeamDto teamDto = teamMapper.toDto(team);

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (!(authentication instanceof AnonymousAuthenticationToken)) {
                        Long currentUserId = authService.getCurrentUser().getId();
                        teamDto.setCanUpdateOrDelete(currentUserId.equals(team.getOwnerId()));
                    }

                    teamDto.setUsers(userMapper.toDtoList(team.getUsers()));

                    return teamDto;
                }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }

    public TeamDto create(TeamCreateDto teamCreateDto) {
        AuthUserDto currentUser = authService.getCurrentUser();
        if ((isUserAlreadyOwner(currentUser.getId()) || isUserInTeam()) || authService.hasAdminRole()) {
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

    public void addUserToTeam(Long teamId, Long userId) {
        AuthUserDto currentUser = authService.getCurrentUser();
        Team team = teamMapper.toEntity(getById(teamId));
        User user = userMapper.toEntity(userService.getById(userId));

        if (!team.getOwnerId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to add users to this team");
        }
        if (user.getTeam().getId() != null) {
            throw new RuntimeException("User is already a member of a team");
        }

        team.setUsers(List.of(user));
        user.setTeam(team);
        userRepository.save(user);
        teamRepository.save(team);
    }

    public void quitFromTeam(Long teamId){
        AuthUserDto currentUser = authService.getCurrentUser();
        User user = userMapper.toEntity(userService.getById(currentUser.getId()));
        Team team = teamMapper.toEntity(getById(teamId));

        if(!Objects.equals(currentUser.getTeam().getId(), teamId)){
            throw new RuntimeException("You are not a member this team");
        }
        if(isUserAlreadyOwner(currentUser.getId())){
            throw new RuntimeException("You are the owner of a team, you can't quit from it");
        }
        user.setTeam(null);
        team.getUsers().remove(user);
        userRepository.save(user);
        teamRepository.save(team);
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
