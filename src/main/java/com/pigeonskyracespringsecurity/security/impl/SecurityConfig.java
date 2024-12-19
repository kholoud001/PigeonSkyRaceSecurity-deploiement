package com.pigeonskyracespringsecurity.security.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints
                        .requestMatchers("/register", "/login", "/oauth2/authorization/**").permitAll()

                        // Protected endpoints (require specific roles)
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/organizer/**").hasAuthority("ROLE_ORGANIZER")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))  // Use custom converter for JWT
                );

        // Enable CORS and disable CSRF (if necessary)
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

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");  // The roles are in this claim
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");  // Ensure the prefix matches what is in the JWT

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return authenticationConverter;
    }

    // Configure the JwtDecoder to validate the JWT using Keycloak's public key
    @Bean
    public JwtDecoder jwtDecoder() {
        String keycloakUrl = "http://keycloak-container:8080/realms/pigeonSecurity/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(keycloakUrl).build();
    }



}
