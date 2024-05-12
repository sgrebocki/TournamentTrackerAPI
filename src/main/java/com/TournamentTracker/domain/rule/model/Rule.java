package com.TournamentTracker.domain.rule.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "`rule`")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "full_time")
    Long fullTime;
    @Column(name = "parts")
    Long parts;
    @Column(name = "break_time")
    Long breakTime;
}
