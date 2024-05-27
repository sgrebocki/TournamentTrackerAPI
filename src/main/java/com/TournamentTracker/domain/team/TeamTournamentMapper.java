package com.TournamentTracker.domain.team;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface TeamTournamentMapper {
    TeamTournamentDto toDto(Team team);
    List<TeamTournamentDto> toDtoList(List<Team> teamList);
    Team toEntity(TeamTournamentDto teamTournamentDto);
}
