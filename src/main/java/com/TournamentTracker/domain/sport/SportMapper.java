package com.TournamentTracker.domain.sport;

import com.TournamentTracker.config.CommonMapperConfig;
import com.TournamentTracker.domain.sport.dto.SportDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CommonMapperConfig.class)
interface SportMapper {
    SportDto toDto(Sport sport);
    List<SportDto> toDtoList(List<Sport> sports);
    Sport toEntity(SportDto sportDto);
}
