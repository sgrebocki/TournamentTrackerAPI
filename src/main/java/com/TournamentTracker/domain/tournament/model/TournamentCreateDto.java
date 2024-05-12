package com.TournamentTracker.domain.tournament.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TournamentCreateDto {
    String name;
    Date dateTime;
    String location;
    String street;
    Long sportId;
}
