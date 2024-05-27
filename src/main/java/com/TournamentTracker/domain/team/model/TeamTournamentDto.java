package com.TournamentTracker.domain.team.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TeamTournamentDto {
    @NotNull(message = "Team id can't be null")
    Integer id;
    @NotNull(message = "Team name can't be null")
    String name;
    @JsonIgnore
    Long tournamentId;
}
