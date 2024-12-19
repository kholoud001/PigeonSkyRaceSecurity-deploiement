package com.pigeonskyracespringsecurity.security.impl;

import com.pigeonskyracespringsecurity.exception.CustomAccessDeniedHandler;
import com.pigeonskyracespringsecurity.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
i
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


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
                .cors().configurationSource(corsConfigurationSource)
                .and()
                .csrf().disable()
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/register", "/login").permitAll();
                    registry.requestMatchers("/admin/**").hasRole("admin");
                    registry.requestMatchers("/user/**").hasAnyRole("admin", "user");
                    registry.requestMatchers("/organizer/**").hasAnyRole("admin", "organizer");
                    registry.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
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




}
