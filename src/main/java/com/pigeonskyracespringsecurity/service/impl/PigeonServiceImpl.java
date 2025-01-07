package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.mapper.PigeonMapper;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.repository.CompetitionRepository;
import com.pigeonskyracespringsecurity.repository.PigeonRepository;
import com.pigeonskyracespringsecurity.repository.UserRepository;
import com.pigeonskyracespringsecurity.service.PigeonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PigeonServiceImpl implements PigeonService {
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final PigeonRepository pigeonRepository;
    private final PigeonMapper pigeonMapper;

    @Override
    @Transactional
    public PigeonDTO addPigeonToCompetition(PigeonDTO pigeonDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Competition competition = competitionRepository.findById(pigeonDTO.getCompetitionId())
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        Pigeon pigeon = pigeonMapper.toEntity(pigeonDTO);
        pigeon.setUser(user);
        pigeon.setCompetition(competition);

        Pigeon savedPigeon = pigeonRepository.save(pigeon);

        PigeonDTO responseDTO = new PigeonDTO();
        responseDTO.setRingNumber(savedPigeon.getRingNumber());
        responseDTO.setAge(savedPigeon.getAge());
        responseDTO.setColor(savedPigeon.getColor());
        responseDTO.setGender(savedPigeon.getGender().toString());
        responseDTO.setCompetitionId(savedPigeon.getCompetition().getId());
        responseDTO.setUserId(savedPigeon.getUser().getId());

        return responseDTO;
    }

    @Override
    public PigeonDTO addPigeon(PigeonDTO pigeonDTO) {
        Pigeon pigeon = pigeonMapper.toEntity(pigeonDTO);

        Pigeon savedPigeon = pigeonRepository.save(pigeon);

        return pigeonMapper.toDto(savedPigeon);
    }

    @Override
    public List<Pigeon> findByRingNumbers(List<String> ringNumbers) {
        return pigeonRepository.findByRingNumberIn(ringNumbers);
    }

    @Override
    public List<Pigeon> saveAll(List<Pigeon> pigeons) {
        return pigeonRepository.saveAll(pigeons);
    }




}
