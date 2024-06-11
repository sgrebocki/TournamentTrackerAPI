package com.TournamentTracker.domain.team.model;

import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import com.TournamentTracker.domain.user.model.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TeamDto {
    @NotNull(message = "Team id can't be null")
    Long id;
    @NotNull(message = "Team name cannot be null")
    String name;
    Long ownerId;
    Boolean canUpdateOrDelete;
    List<UserDto> users;
    TournamentTeamDto tournament;
}
