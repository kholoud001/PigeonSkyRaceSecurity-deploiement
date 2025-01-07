package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import com.pigeonskyracespringsecurity.repository.RoleRepository;
import com.pigeonskyracespringsecurity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findRoleByType(RoleType roleType) {
        return roleRepository.findByRoleType(roleType);
    }
}
