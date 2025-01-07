package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.enums.RoleType;

import java.util.Optional;

public interface RoleService {
    Role saveRole(Role role);

    Optional<Role> findRoleByType(RoleType roleType);
}
