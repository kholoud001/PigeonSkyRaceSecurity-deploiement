package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.mapper.CompetitionMapper;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.repository.CompetitionRepository;
import com.pigeonskyracespringsecurity.repository.UserRepository;
import com.pigeonskyracespringsecurity.service.CompetitionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    @Override
    @Transactional
    public Competition createCompetition(CompetitionDTO competitionDTO) {
        User user = userRepository.findById(competitionDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition.setUser(user);

        return competitionRepository.save(competition);
    }

    @Override
    @Transactional
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    @Transactional
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found!"));
    }

    @Override
    @Transactional
    public void deleteCompetition(Long id) {
        if (!competitionRepository.existsById(id)) {
            throw new IllegalArgumentException("Competition not found!");
        }
        competitionRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Competition updateCompetition(Long id, CompetitionDTO competitionDTO) {
        Competition existingCompetition = competitionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found!"));

        existingCompetition.setName(competitionDTO.getName());
        existingCompetition.setLatitude(competitionDTO.getLatitude());
        existingCompetition.setLongitude(competitionDTO.getLongitude());
        existingCompetition.setReleasePlace(competitionDTO.getReleasePlace());
        existingCompetition.setPigeonCount(competitionDTO.getPigeonCount());
        existingCompetition.setDepartureTime(competitionDTO.getDepartureTime());

        return competitionRepository.save(existingCompetition);
    }

    @Override
    @Transactional
    public List<Competition> searchCompetitionsByName(String name) {
        return competitionRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Competition> findById(Long competitionId){
        return competitionRepository.findById(competitionId);
    }






}
