package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.Tournament;
import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = CustomMapperConfig.class)
public interface TournamentMapper {
    TournamentDto toDto(Tournament tournament);
    Tournament toEntity(TournamentDto tournamentDto);
    @Mapping(source = "sportId", target = "sport.id")
    Tournament toEntity(TournamentCreateDto tournamentCreateDto);
    Tournament toEntity(TournamentTeamDto tournamentTeamDto);
    List<TournamentDto> toDtoList(List<Tournament> tournaments);


    TournamentTeamDto toTournamentTeamDto(Tournament tournament);
    List<TournamentTeamDto> toTournamentTeamDtoList(List<Tournament> tournamentList);
}
