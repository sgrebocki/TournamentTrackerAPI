package com.TournamentTracker.domain.game.model;

import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.tournament.model.Tournament;
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
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "game_time")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    LocalDateTime gameTime;
    @OneToOne
    @JoinColumn(name = "home_team_id")
    Team homeTeam;
    @Column(name = "home_team_score")
    Long homeTeamScore;
    @OneToOne
    @JoinColumn(name = "guest_team_id")
    Team guestTeam;
    @Column(name = "guest_team_score")
    Long guestTeamScore;
    @OneToOne
    @JoinColumn(name = "tournament_id")
    Tournament tournament;

    public void setGameTimeToCurrentTime() {
        this.gameTime = LocalDateTime.now();
    }
}
