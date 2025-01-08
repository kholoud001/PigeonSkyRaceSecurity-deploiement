package com.pigeonskyracespringsecurity.security.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.addFilterAfter(new DebugFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/register", "/login", "/oauth2/authorization/**","/competitions/").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/organizer/**").hasAuthority("ROLE_ORGANIZER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        http.cors(customizer -> customizer.configurationSource(corsConfigurationSource));
        http.csrf(customizer -> customizer.disable());

        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://localhost:8443");
        configuration.addAllowedHeader("Authorization, Origin, Content-Type, Accept, cache-control");
        configuration.addAllowedMethod("HEAD, GET, PUT, POST, OPTIONS");
        configuration.addExposedHeader("Authorization, Cache-Control");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
//        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return authenticationConverter;
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

        // Custom authority converter to extract roles manually
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract claims from the JWT
            var claims = jwt.getClaims();

            // Print all the claims for debugging
            System.out.println("DEBUG: JWT Claims: " + claims);

            // Extract roles from the 'realm_access.roles' claim
            Object rolesClaim = claims.get("realm_access");
            if (rolesClaim != null) {
                Map<String, Object> realmAccess = (Map<String, Object>) rolesClaim;
                List<String> roles = (List<String>) realmAccess.get("roles");

                // Print the roles for debugging
                System.out.println("DEBUG: Extracted roles: " + roles);

                // Manually convert roles to authorities
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());

                // Print the authorities for debugging
                System.out.println("DEBUG: Extracted authorities:");
                authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

                return authorities;
            }

            return Collections.emptyList();
        });

        return authenticationConverter;
    }




    @Bean
    public JwtDecoder jwtDecoder() {
        String keycloakUrl = "http://keycloak-container:8080/realms/pigeonSecurity/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(keycloakUrl).build();
    }



}
