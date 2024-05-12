package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.model.SportCreateDto;
import com.TournamentTracker.domain.sport.model.SportDto;

import java.util.List;

public interface SportService {
    List<SportDto> getAll();
    SportDto getById(Long id);
    SportDto create(SportCreateDto sportDto);
    SportDto update(SportDto sportDto, Long id);
    void deleteById(Long id);
}
