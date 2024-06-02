package com.TournamentTracker.domain.tournament.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TournamentCreateDto {
    String name;
    LocalDateTime dateTime;
    String location;
    String street;
    Long ownerId;
    Long sportId;
}
