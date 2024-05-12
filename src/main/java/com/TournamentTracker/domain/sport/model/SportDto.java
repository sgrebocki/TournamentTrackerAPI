package com.TournamentTracker.domain.sport.model;

import com.TournamentTracker.domain.rule.model.RuleDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SportDto {
    @NotNull(message = "Sport id cannot be empty")
    Long id;
    String sportName;
    RuleDto rule;
}
