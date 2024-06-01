package com.TournamentTracker.domain.team.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamCreateDto {
    @NotNull(message = "Team name cannot be null")
    String name;
    Long ownerId;
}
