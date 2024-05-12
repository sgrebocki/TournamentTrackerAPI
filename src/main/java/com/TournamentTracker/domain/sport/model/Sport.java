package com.TournamentTracker.domain.sport.model;

import com.TournamentTracker.domain.rule.model.Rule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "sport")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String sportName;
    @OneToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;
}
