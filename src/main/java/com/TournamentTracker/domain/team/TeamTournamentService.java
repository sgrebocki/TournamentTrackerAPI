package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;

public interface TeamTournamentService {
    TeamTournamentDto update(TeamDto teamDto, Long id);
}
