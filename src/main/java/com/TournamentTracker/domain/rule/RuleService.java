package com.TournamentTracker.domain.rule;

import com.TournamentTracker.domain.rule.model.RuleDto;

import java.util.List;

public interface RuleService {
    List<RuleDto> getAll();
    RuleDto getById(Long id);
    RuleDto create(RuleDto ruleDto);
    RuleDto update(RuleDto ruleDto, Long id);
    void deleteById(Long id);
}
