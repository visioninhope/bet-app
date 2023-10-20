package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.MatchResultDTO;
import com.jeleniasty.betapp.features.competition.CompetitionService;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final TeamService teamService;
  private final CompetitionService competitionService;
  private final ResultService resultService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public void saveMatch(SaveMatchDTO matchDTO) {
    var competition = competitionService.fetchCompetition(
      matchDTO.competitionId()
    );

    var homeTeam = teamService.fetchTeam(matchDTO.homeTeamId());
    var awayTeam = teamService.fetchTeam(matchDTO.awayTeamId());

    var newMatch = new Match(
      matchDTO.status(),
      matchDTO.stage(),
      matchDTO.group(),
      matchDTO.homeOdds(),
      matchDTO.awayOdds(),
      matchDTO.utcDate()
    );
    newMatch.assignCompetition(competition);
    newMatch.assignAwayTeam(awayTeam);
    newMatch.assignHomeTeam(homeTeam);
  }

  public Match fetchMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> new MatchNotFoundException(matchId));
  }

  @Transactional
  public void setMatchResult(MatchResultDTO matchResultDTO) {
    var result = resultService.saveResult(matchResultDTO.saveResultDTO());
    var matchToBeUpdated = fetchMatch(matchResultDTO.matchId());

    matchToBeUpdated.setResult(result);
    eventPublisher.publishEvent(
      new MatchResultSetEvent(matchToBeUpdated.getId())
    );
  }
}
