package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface PigeonService {
    //Pigeon addPigeonToCompetition(PigeonDTO pigeonDTO) throws AccessDeniedException;
    //PigeonDTO addPigeonToCompetition(PigeonDTO pigeonDTO)throws AccessDeniedException;;

    PigeonDTO addPigeon(PigeonDTO pigeonDTO);

    List<Pigeon> findByRingNumbers(List<String> ringNumbers);

    List<Pigeon> saveAll(List<Pigeon> pigeons);

    PigeonDTO updatePigeon(Long pigeonId, PigeonDTO pigeonDTO);

    void deletePigeon(Long pigeonId);

    PigeonDTO getPigeonById(Long pigeonId);

    List<PigeonDTO> getAllPigeons();

    List<PigeonDTO> getPigeonsByUserId(Long userId);
}
