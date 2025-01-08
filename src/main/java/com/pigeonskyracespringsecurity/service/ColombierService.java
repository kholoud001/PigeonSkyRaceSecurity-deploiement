package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import jakarta.transaction.Transactional;

public interface ColombierService {

    //ColombierDTO createColombier(ColombierDTO colombierDTO);

    @Transactional
    Colombier addColombier(Long userId, Colombier colombier);

    ColombierDTO getColombierById(Long id);

    ColombierDTO updateColombier(Long id, ColombierDTO colombierDTO);


    @Transactional
    void deleteColombier(Long colombierId, Long userId);
}
