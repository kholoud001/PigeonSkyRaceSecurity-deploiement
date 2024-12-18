package com.pigeonskyracespringsecurity.config;

import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import com.pigeonskyracespringsecurity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class RoleInitializer {
    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(null, RoleType.ROLE_USER, new ArrayList<>()));
                roleRepository.save(new Role(null, RoleType.ROLE_ADMIN, new ArrayList<>()));
                roleRepository.save(new Role(null, RoleType.ROLE_ORGANIZER, new ArrayList<>()));
            }
        };
    }
}
