package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.config.UnauthorizedException;
import com.pigeonskyracespringsecurity.mapper.ColombierMapper;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.repository.ColombierRepository;
import com.pigeonskyracespringsecurity.repository.UserRepository;
import com.pigeonskyracespringsecurity.service.ColombierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColombierServiceImpl implements ColombierService {

    private final ColombierRepository colombierRepository;
    private final UserRepository userRepository;
    private final ColombierMapper colombierMapper;

    @Override
    @Transactional
    public Colombier addColombier(Long userId, Colombier colombier) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the user who created the Colombier
        colombier.setUser(user);

        return colombierRepository.save(colombier);
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
    @Transactional
    public void deleteColombier(Long colombierId, Long userId) {
        Colombier colombier = colombierRepository.findById(colombierId)
                .orElseThrow(() -> new IllegalArgumentException("Colombier not found"));

        if (!colombier.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only delete your own Colombier");
        }

        colombierRepository.deleteById(colombierId);
    }

    @Override
    public List<ColombierDTO> getColombiersByUserId(Long userId) {
        return colombierRepository.findByUserId(userId)
                .stream()
                .map(colombierMapper::toDto)
                .collect(Collectors.toList());
    }

}
