package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.mapper.PigeonMapper;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.model.enums.Gender;
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
        // Fetch the user by userId from the DTO
        User user = userRepository.findById(pigeonDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch the colombier associated with the user
        Colombier colombier = user.getColombier();
        if (colombier == null) {
            throw new IllegalArgumentException("User does not have a colombier.");
        }

        // Create a new Pigeon entity
        Pigeon pigeon = new Pigeon();
        pigeon.setRingNumber(pigeonDTO.getRingNumber());
        pigeon.setGender(Gender.valueOf(pigeonDTO.getGender()));
        pigeon.setAge(pigeonDTO.getAge());
        pigeon.setColor(pigeonDTO.getColor());
        pigeon.setColombier(colombier); 

        // Save the pigeon to the repository
        Pigeon savedPigeon = pigeonRepository.save(pigeon);

        // Map the saved Pigeon entity back to PigeonDTO
        PigeonDTO savedPigeonDTO = new PigeonDTO();
        savedPigeonDTO.setRingNumber(savedPigeon.getRingNumber());
        savedPigeonDTO.setGender(savedPigeon.getGender().name());
        savedPigeonDTO.setAge(savedPigeon.getAge());
        savedPigeonDTO.setColor(savedPigeon.getColor());
        savedPigeonDTO.setUserId(savedPigeon.getUser().getId());
        savedPigeonDTO.setColombierId(savedPigeon.getColombier().getId());

        return savedPigeonDTO;
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
