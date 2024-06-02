package com.TournamentTracker.domain.game;

import com.TournamentTracker.domain.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByTournament_Id(Long tournamentId);
}
