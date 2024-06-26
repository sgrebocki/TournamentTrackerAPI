package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;

import java.util.List;

public interface TeamTournamentService {
    TeamTournamentDto signUpForTournament(Long tournamentId);
    TeamTournamentDto signOutFromTournament(Long tournamentId);
}
