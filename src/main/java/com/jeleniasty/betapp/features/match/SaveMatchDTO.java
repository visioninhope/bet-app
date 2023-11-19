package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.LocalDateTime;

public record SaveMatchDTO(
  MatchStatus status,
  CompetitionStage stage,
  Group group,
  float homeOdds,
  float awayOdds,
  float drawOdds,
  LocalDateTime utcDate,
  CompetitionDTO competitionDTO,
  TeamDTO homeTeamDTO,
  TeamDTO awayTeamDTO
) {}
