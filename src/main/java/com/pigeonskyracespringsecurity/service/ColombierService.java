package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.ColombierDTO;

public interface ColombierService {

    ColombierDTO createColombier(ColombierDTO colombierDTO);

    ColombierDTO getColombierById(Long id);

    ColombierDTO updateColombier(Long id, ColombierDTO colombierDTO);

    void deleteColombier(Long id);
}
