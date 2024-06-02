package com.TournamentTracker.domain.tournament.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class TournamentTeamDto {
    Long id;
    String name;
    LocalDateTime dateTime;
    String location;
    String street;
    Long ownerId;
}
