package com.TournamentTracker.domain.team;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface TeamMapper {
    TeamDto toDto(Team team);
    List<TeamDto> toDtoList(List<Team> teamList);
    Team toEntity(TeamDto teamDto);

    Team toEntity(TeamCreateDto teamDto);
    Team toEntity(TeamTournamentDto tournamentDto);

    @Mapping(source = "tournament.id", target = "tournamentId")
    TeamTournamentDto toTeamTournamentDto(Team team);
    List<TeamTournamentDto> toTeamTournamentDtoList(List<Team> teams);

}


