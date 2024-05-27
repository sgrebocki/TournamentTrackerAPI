package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TeamRepository extends JpaRepository<Team, Long> {
}
