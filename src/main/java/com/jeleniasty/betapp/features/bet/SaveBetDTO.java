package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.SaveResultDTO;

public record SaveBetDTO(SaveResultDTO saveResultDTO, Long matchId) {}
