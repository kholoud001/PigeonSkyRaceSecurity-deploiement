package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface PigeonService {
    //Pigeon addPigeonToCompetition(PigeonDTO pigeonDTO) throws AccessDeniedException;
    PigeonDTO addPigeonToCompetition(PigeonDTO pigeonDTO)throws AccessDeniedException;;

    PigeonDTO addPigeon(PigeonDTO pigeonDTO);

    List<Pigeon> findByRingNumbers(List<String> ringNumbers);

    List<Pigeon> saveAll(List<Pigeon> pigeons);

//    PigeonDTO addPigeon(String ringNumber, String gender, int age, String color, Long userId);
}
