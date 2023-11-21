package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.Match;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "competition")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class Competition {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "competition_id_seq",
    sequenceName = "competition_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "competition_id_seq"
  )
  private Long id;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "code")
  private String code;

  @NotNull
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CompetitionType type;

  @NotNull
  @Column(name = "season")
  private Integer season;

  @OneToMany(
    mappedBy = "competition",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Match> competitionMatches = new HashSet<>();

  public Competition(
    @NotNull String name,
    @NotNull String code,
    @NotNull CompetitionType type,
    @NotNull Integer season
  ) {
    this.name = name;
    this.code = code;
    this.type = type;
    this.season = season;
  }

  public void assignMatches(Set<Match> matches) {
    this.competitionMatches.addAll(matches);
    matches.forEach(match -> match.assignCompetition(this));
  }
}
