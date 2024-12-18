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

}
