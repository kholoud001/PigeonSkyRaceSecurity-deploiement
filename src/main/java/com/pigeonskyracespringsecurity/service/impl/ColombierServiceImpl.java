package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.mapper.ColombierMapper;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.repository.ColombierRepository;
import com.pigeonskyracespringsecurity.repository.UserRepository;
import com.pigeonskyracespringsecurity.service.ColombierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColombierServiceImpl implements ColombierService {

    private final ColombierRepository colombierRepository;
    private final UserRepository userRepository;
    private final ColombierMapper colombierMapper;

    @Override
    public ColombierDTO createColombier(ColombierDTO colombierDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getColombier() != null) {
            throw new IllegalStateException("User already has a colombier.");
        }

        Colombier colombier = colombierMapper.toEntity(colombierDTO);
        colombier = colombierRepository.save(colombier);

        user.setColombier(colombier);
        userRepository.save(user);

        return colombierMapper.toDto(colombier);
    }

    @Override
    public ColombierDTO getColombierById(Long id) {
        Colombier colombier = colombierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colombier not found"));
        return colombierMapper.toDto(colombier);
    }

    @Override
    public ColombierDTO updateColombier(Long id, ColombierDTO colombierDTO) {
        Colombier colombier = colombierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colombier not found"));

        colombier.setName(colombierDTO.getName());
        colombier.setLocation(colombierDTO.getLocation());
        colombier.setLatitude(colombierDTO.getLatitude());
        colombier.setLongitude(colombierDTO.getLongitude());

        colombier = colombierRepository.save(colombier);
        return colombierMapper.toDto(colombier);
    }

    @Override
    public void deleteColombier(Long id) {
        Colombier colombier = colombierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colombier not found"));

        colombierRepository.delete(colombier);
    }
}
