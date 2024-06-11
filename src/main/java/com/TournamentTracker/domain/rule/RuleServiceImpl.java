package com.TournamentTracker.domain.rule;

import com.TournamentTracker.domain.rule.model.RuleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.TournamentTracker.util.ExceptionMessages.RULE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
class RuleServiceImpl implements RuleService{
    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;

    public List<RuleDto> getAll() {
        return ruleMapper.toDtoList(ruleRepository.findAll());
    }

    public RuleDto getById(Long id) {
        return ruleRepository.findById(id)
                .map(ruleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(RULE_NOT_FOUND, id)));
    }

    public RuleDto create(RuleDto ruleDto) {
        return ruleMapper.toDto(ruleRepository.save(ruleMapper.toEntity(ruleDto)));
    }

    public RuleDto update(RuleDto ruleDto, Long id) {
        return ruleRepository.findById(id)
                .map(editRule -> {
                    editRule.setId(id);
                    editRule.setBreakTime(ruleDto.getBreakTime());
                    editRule.setFullTime(ruleDto.getFullTime());
                    editRule.setParts(ruleDto.getParts());
                    return ruleMapper.toDto(ruleRepository.save(editRule));
                }).orElseThrow(() -> new EntityNotFoundException(String.format(RULE_NOT_FOUND, id)));
    }

    public void deleteById(Long id) {
        ruleRepository.deleteById(id);
    }
}
