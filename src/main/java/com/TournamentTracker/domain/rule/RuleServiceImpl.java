package com.TournamentTracker.domain.rule;

import com.TournamentTracker.domain.rule.model.RuleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class RuleServiceImpl implements RuleService{
    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;

    public List<RuleDto> getAll() {
        return ruleMapper.toDtoList(ruleRepository.findAll());
    }

    public RuleDto getById(Long id) {
        return ruleRepository.findById(id)
                .map(ruleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Rule with id " + id + "not found"));
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
                    return ruleMapper.toDto(ruleRepository.save(ruleMapper.toEntity(ruleDto)));
                }).orElseThrow(() -> new EntityNotFoundException("Rule with id " + id + "not found"));
    }

    public void deleteById(Long id) {
        ruleRepository.deleteById(id);
    }
}
