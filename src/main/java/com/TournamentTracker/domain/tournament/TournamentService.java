package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.tournament.model.TournamentCreateDto;
import com.TournamentTracker.domain.tournament.model.TournamentDto;

import java.util.List;

public interface TournamentService {
    List<TournamentDto> getAll();
    TournamentDto getById(Long id);
    TournamentDto create(TournamentCreateDto tournamentDto);
    TournamentDto update(TournamentCreateDto tournamentDto, Long id);
    void deleteById(Long id);
}
