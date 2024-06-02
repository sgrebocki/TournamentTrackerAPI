package com.TournamentTracker.domain.tournament.model;

import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.sport.model.SportDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TournamentDto {
    Long id;
    String name;
    LocalDateTime dateTime;
    String location;
    String street;
    Long ownerId;
    Boolean canUpdateOrDelete;
    SportDto sport;
    List<GameTournamentDto> gamesList;
    List<TeamTournamentDto> teamsList;
}
