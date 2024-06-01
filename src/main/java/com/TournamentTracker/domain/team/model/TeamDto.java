package com.TournamentTracker.domain.team.model;

import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TeamDto {
    @NotNull(message = "Team id can't be null")
    Long id;
    @NotNull(message = "Team name cannot be null")
    String name;
    Long ownerId;
    TournamentTeamDto tournament;
}
