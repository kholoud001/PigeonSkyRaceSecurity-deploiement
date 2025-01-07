package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CompetitionService {
    @Transactional
    Competition createCompetition(CompetitionDTO competitionDTO);

    @Transactional
    List<Competition> getAllCompetitions();

    @Transactional
    Competition getCompetitionById(Long id);

    @Transactional
    void deleteCompetition(Long id);

    @Transactional
    Competition updateCompetition(Long id, CompetitionDTO competitionDTO);

    @Transactional
    List<Competition> searchCompetitionsByName(String name);

    Optional<Competition> findById(Long competitionId);
}
