package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.tournament.model.Tournament;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "game_time")
    Date gameTime;
    @Column(name = "game_score")
    String score;
    @OneToOne
    @JoinColumn(name = "tournament_id")
    Tournament tournament;
}
