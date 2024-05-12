package com.TournamentTracker.domain.rule;

import com.TournamentTracker.domain.rule.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RuleRepository extends JpaRepository<Rule, Long> {
}
