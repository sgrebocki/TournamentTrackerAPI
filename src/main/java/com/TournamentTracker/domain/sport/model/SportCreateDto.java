package com.TournamentTracker.domain.sport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SportCreateDto {
    String sportName;
    Long ruleId;
}
