package com.TournamentTracker.domain.tournament.model;

import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.sport.model.SportDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class TournamentDto {
    Long id;
    String name;
    Date dateTime;
    String location;
    String street;
    SportDto sport;
    List<GameTournamentDto> gamesList;
    List<TeamTournamentDto> teamsList;
}
