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
import lombok.RequiredArgsConstructor;
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
    public PigeonDTO addPigeon(PigeonDTO pigeonDTO) {
        User user = userRepository.findById(pigeonDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Colombier colombier = user.getColombier();
        if (colombier == null) {
            throw new IllegalArgumentException("User does not have a colombier.");
        }

        Pigeon pigeon = new Pigeon();
        pigeon.setRingNumber(pigeonDTO.getRingNumber());
        pigeon.setGender(Gender.valueOf(pigeonDTO.getGender()));
        pigeon.setAge(pigeonDTO.getAge());
        pigeon.setColor(pigeonDTO.getColor());
        pigeon.setColombier(colombier);

        pigeon.setUser(user);

        if (pigeonDTO.getCompetitionId() != null) {
            Competition competition = competitionRepository.findById(pigeonDTO.getCompetitionId())
                    .orElseThrow(() -> new IllegalArgumentException("Competition not found"));
            pigeon.setCompetition(competition);
        }

        Pigeon savedPigeon = pigeonRepository.save(pigeon);

        PigeonDTO savedPigeonDTO = new PigeonDTO();
        savedPigeonDTO.setRingNumber(savedPigeon.getRingNumber());
        savedPigeonDTO.setGender(savedPigeon.getGender().name());
        savedPigeonDTO.setAge(savedPigeon.getAge());
        savedPigeonDTO.setColor(savedPigeon.getColor());
        savedPigeonDTO.setUserId(savedPigeon.getUser().getId());
        savedPigeonDTO.setColombierId(savedPigeon.getColombier().getId());
        if (savedPigeon.getCompetition() != null) {
            savedPigeonDTO.setCompetitionId(savedPigeon.getCompetition().getId());
        }

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

    @Override
    public PigeonDTO updatePigeon(Long pigeonId, PigeonDTO pigeonDTO) {
        Pigeon existingPigeon = pigeonRepository.findById(pigeonId)
                .orElseThrow(() -> new IllegalArgumentException("Pigeon not found"));

        if (pigeonDTO.getRingNumber() != null) existingPigeon.setRingNumber(pigeonDTO.getRingNumber());
        if (pigeonDTO.getGender() != null) existingPigeon.setGender(Gender.valueOf(pigeonDTO.getGender()));
        if (pigeonDTO.getAge() != 0) existingPigeon.setAge(pigeonDTO.getAge());
        if (pigeonDTO.getColor() != null) existingPigeon.setColor(pigeonDTO.getColor());

        if (pigeonDTO.getCompetitionId() != null) {
            Competition competition = competitionRepository.findById(pigeonDTO.getCompetitionId())
                    .orElseThrow(() -> new IllegalArgumentException("Competition not found"));
            existingPigeon.setCompetition(competition);
        }

        Pigeon updatedPigeon = pigeonRepository.save(existingPigeon);
        return pigeonMapper.toDto(updatedPigeon);
    }

    @Override
    public void deletePigeon(Long pigeonId) {
        Pigeon pigeon = pigeonRepository.findById(pigeonId)
                .orElseThrow(() -> new IllegalArgumentException("Pigeon not found"));
        pigeonRepository.delete(pigeon);
    }

    @Override
    public PigeonDTO getPigeonById(Long pigeonId) {
        Pigeon pigeon = pigeonRepository.findById(pigeonId)
                .orElseThrow(() -> new IllegalArgumentException("Pigeon not found"));
        return pigeonMapper.toDto(pigeon);
    }

    @Override
    public List<PigeonDTO> getAllPigeons() {
        List<Pigeon> pigeons = pigeonRepository.findAll();
        return pigeons.stream().map(pigeonMapper::toDto).toList();
    }





}
