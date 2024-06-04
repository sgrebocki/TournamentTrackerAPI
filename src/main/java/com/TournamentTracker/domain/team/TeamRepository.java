package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByOwnerId(Long ownerId);
    List<Team> findAllByOwnerId(Long ownerId);
}
