package com.pigeonskyracespringsecurity.repository;

import com.pigeonskyracespringsecurity.model.entity.Colombier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColombierRepository extends JpaRepository<Colombier, Long> {
    List<Colombier> findByUserId(Long userId);

}
