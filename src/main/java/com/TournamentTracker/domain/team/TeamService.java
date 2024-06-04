package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;

import java.util.List;

public interface TeamService {
    List<TeamDto> getAll();
    TeamDto getById(Long id);
    TeamDto create(TeamCreateDto teamCreateDto);
    TeamDto update(TeamDto teamDto, Long id);
    void deleteById(Long id);
    List<TeamTournamentDto> getOnlyTeams();
    TeamDto getOwnedTeam(Long userId);
}
