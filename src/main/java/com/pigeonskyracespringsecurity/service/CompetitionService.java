package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import jakarta.transaction.Transactional;

public interface CompetitionService {
    @Transactional
    Competition createCompetition(CompetitionDTO competitionDTO);
}
