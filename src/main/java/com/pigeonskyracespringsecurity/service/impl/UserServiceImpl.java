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
import org.springframework.security.core.userdetails.UserDetails;
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

//        Role role = roleRepository.findByRoleType(userDTO.getRoleType())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid role type!"));
        Role role = roleRepository.findByRoleType(RoleType.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found!"));


        User user = userMapper.toUser(userDTO);
        user.setUsername(userDTO.getUsername());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }



//    @Override
//    public User changeRole(String username, String newRole) {
//        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (currentUser == null || !currentUser.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
//            throw new AccessDeniedException("Only admins can change user roles");
//        }
//
//        System.out.println("Current User Authorities: " + currentUser.getAuthorities());
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        Role role = roleRepository.findByRoleType(RoleType.valueOf(newRole))
//                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
//
//        user.setRole(role);
//        return userRepository.save(user);
//    }

    @Override
    public User changeRole(String username, String newRole) {
        // Get the current JWT token from the SecurityContext
        Jwt currentUserJwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUserJwt == null) {
            throw new AccessDeniedException("No authentication token available.");
        }

        Map<String, Object> realmAccess = currentUserJwt.getClaimAsMap("realm_access");

        // Extract roles, check for null or empty
        List<String> roles = null;
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Object rolesObj = realmAccess.get("roles");

            // Safely check if the roles claim is of type List<String>
            if (rolesObj instanceof List) {
                roles = (List<String>) rolesObj;
            } else {
                throw new IllegalStateException("Roles are not in expected format.");
            }
        }

        // If roles are null or empty, deny access
        if (roles == null || !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only admins can change user roles");
        }

        System.out.println("Current User Roles: " + roles);

        // Fetch the user to be updated
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch the new role
        Role role = roleRepository.findByRoleType(RoleType.valueOf(newRole))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Update the user's role
        user.setRole(role);
        return userRepository.save(user);
    }



    @Override
    public Optional<User> findByUsername(String name){
        return userRepository.findByUsername(name);
    }


}
