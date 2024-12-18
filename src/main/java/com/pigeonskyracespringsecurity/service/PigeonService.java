package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;

import java.nio.file.AccessDeniedException;

public interface PigeonService {
    //Pigeon addPigeonToCompetition(PigeonDTO pigeonDTO) throws AccessDeniedException;
    PigeonDTO addPigeonToCompetition(PigeonDTO pigeonDTO)throws AccessDeniedException;;
}
