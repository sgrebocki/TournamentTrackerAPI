package com.TournamentTracker.domain.rule;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.rule.model.Rule;
import com.TournamentTracker.domain.rule.model.RuleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface RuleMapper {
    RuleDto toDto(Rule rule);
    List<RuleDto> toDtoList(List<Rule> ruleList);
    Rule toEntity(RuleDto ruleDto);
}
