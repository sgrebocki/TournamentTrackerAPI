package com.TournamentTracker.domain.tournament.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class TournamentTeamDto {
    Long id;
    String name;
    Date dateTime;
    String location;
    String street;
}
