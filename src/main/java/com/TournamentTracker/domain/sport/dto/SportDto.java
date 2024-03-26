package com.TournamentTracker.domain.sport.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SportDto {
    @NotNull(message = "Sport id cannot be empty")
    private Long id;
    private String sportName;
}
