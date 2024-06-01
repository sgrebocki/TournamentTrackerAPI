package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;

import java.util.List;

public interface TournamentService {
    List<TournamentDto> getAll();
    TournamentDto getById(Long id);
    TournamentDto create(TournamentCreateDto tournamentDto);
    TournamentDto update(TournamentCreateDto tournamentDto, Long id);
    void deleteById(Long id);
    List<GameTournamentDto> getMappedGames(TournamentDto tournamentDto);
    List<TeamTournamentDto> getMappedTeams(TournamentDto tournamentDto);
}
