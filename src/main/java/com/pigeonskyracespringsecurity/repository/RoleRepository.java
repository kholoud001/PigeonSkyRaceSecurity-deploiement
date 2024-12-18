package com.pigeonskyracespringsecurity.repository;

import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);
}
