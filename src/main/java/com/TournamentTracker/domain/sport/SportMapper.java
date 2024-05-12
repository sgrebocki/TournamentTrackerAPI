package com.TournamentTracker.domain.sport;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.sport.model.Sport;
import com.TournamentTracker.domain.sport.model.SportCreateDto;
import com.TournamentTracker.domain.sport.model.SportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface SportMapper {
    SportDto toDto(Sport sport);
    List<SportDto> toDtoList(List<Sport> sports);
    Sport toEntity(SportDto sportDto);
    @Mapping(source = "ruleId", target = "rule.id")
    Sport toEntity(SportCreateDto sportCreateDto);
}
