package com.TournamentTracker.domain.tournament.model;

import com.TournamentTracker.domain.sport.model.Sport;
import com.TournamentTracker.util.LocalDateTimeAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_time")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime dateTime;
    @Column(name = "location")
    private String location;
    @Column(name = "street")
    private String street;
    @Column(name = "owner_id")
    private Long ownerId;
    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;
}
