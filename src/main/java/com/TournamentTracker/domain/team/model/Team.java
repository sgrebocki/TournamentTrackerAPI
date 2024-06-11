package com.TournamentTracker.domain.team.model;

import com.TournamentTracker.domain.tournament.model.Tournament;
import com.TournamentTracker.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "owner_id")
    private Long ownerId;
    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    @OneToMany(mappedBy = "team")
    private List<User> users;
    public Team(Long teamId) {
    }
}
