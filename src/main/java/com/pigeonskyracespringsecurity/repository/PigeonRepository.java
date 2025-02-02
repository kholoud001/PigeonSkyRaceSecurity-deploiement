package com.pigeonskyracespringsecurity.repository;

import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PigeonRepository extends JpaRepository<Pigeon, Long> {
    Optional<Pigeon> findByRingNumber(String ringNumber);

    List<Pigeon> findByRingNumberIn(List<String> ringNumbers);

    List<Pigeon> findByUserId(Long userId);
}
