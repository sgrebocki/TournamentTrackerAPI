package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GameRepository extends JpaRepository<Game, Long> {
}
