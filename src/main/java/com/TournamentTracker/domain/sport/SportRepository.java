package com.TournamentTracker.domain.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SportRepository extends JpaRepository<Sport, Long> {

}
