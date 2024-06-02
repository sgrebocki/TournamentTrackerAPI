package com.TournamentTracker.domain.tournament;

import com.TournamentTracker.domain.tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByOwnerId(Long ownerId);
}
