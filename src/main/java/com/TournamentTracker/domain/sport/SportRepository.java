package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SportRepository extends JpaRepository<Sport, Long> {

}
