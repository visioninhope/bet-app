package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.match.dto.UpcomingMatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
  @Query(
    value = "SELECT m.id, ht.name as homeTeam, ht.flag as homeFlag, at.name as awayTeam, at.flag as awayFlag , m.home_odds as homeOdds, m.away_odds as awayOdds, m.date as matchDate FROM betapp.match m JOIN betapp.team ht ON ht.id = m.home_team JOIN betapp.team at ON at.id = m.away_team WHERE m.status='TIMED' ORDER BY m.date LIMIT 10;",
    nativeQuery = true
  )
  List<UpcomingMatchDTO> findTop10ByStatusOrderByDate();

  Optional<Match> findByHomeTeamNameContainingAndAwayTeamNameContainingAndDate(
    @Param("homeTeamName") String homeTeamName,
    @Param("awayTeamName") String awayTeamName,
    @Param("date") LocalDateTime date
  );

  List<Match> findAllByDateBetween(
    LocalDateTime startDate,
    LocalDateTime endDate
  );
}
