package com.TournamentTracker.config;

import org.mapstruct.*;

@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomMapperConfig {
}
