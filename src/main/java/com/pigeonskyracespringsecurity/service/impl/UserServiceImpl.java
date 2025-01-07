package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.exception.UsernameAlreadyExistsException;
import com.pigeonskyracespringsecurity.mapper.UserMapper;
import com.pigeonskyracespringsecurity.model.entity.*;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import com.pigeonskyracespringsecurity.repository.RoleRepository;
import com.pigeonskyracespringsecurity.repository.UserRepository;
import com.pigeonskyracespringsecurity.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User register(UserDTO userDTO) throws UsernameAlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(userDTO.getUsername());
        }

        Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found!"));

        // Use UserMapper to map DTO to User entity
        User user = userMapper.toUser(userDTO);
        user.setUsername(userDTO.getUsername());
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getColombierName() != null) {
            Colombier colombier = new Colombier();
            colombier.setName(userDTO.getColombierName());
            colombier.setLatitude(userDTO.getLatitude());
            colombier.setLongitude(userDTO.getLongitude());
            user.setColombier(colombier);
        }

        return userRepository.save(user);
    }


    @Override
    public User changeRole(String username, String newRole) {
        Jwt currentUserJwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUserJwt == null) {
            throw new AccessDeniedException("No authentication token available.");
        }
        Map<String, Object> realmAccess = currentUserJwt.getClaimAsMap("realm_access");

        List<String> roles = null;
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Object rolesObj = realmAccess.get("roles");

            if (rolesObj instanceof List) {
                roles = (List<String>) rolesObj;
            } else {
                throw new IllegalStateException("Roles are not in expected format.");
            }
        }
        if (roles == null || !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only admins can change user roles");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Role role = roleRepository.findByRoleType(RoleType.valueOf(newRole))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        user.setRole(role);
        return userRepository.save(user);
    }



    @Override
    public Optional<User> findByUsername(String name){

        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
