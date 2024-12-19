package com.pigeonskyracespringsecurity.security.impl;

import com.pigeonskyracespringsecurity.exception.CustomAccessDeniedHandler;
import com.pigeonskyracespringsecurity.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@Profile("prod")
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler  customAccessDeniedHandler;


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/register").permitAll();
//                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
//                    registry.requestMatchers("/competitions/**").hasAnyRole("ADMIN", "ORGANIZER");
//                    registry.requestMatchers("/pigeons/**").hasAnyRole("ADMIN","USER");
//                    registry.anyRequest().authenticated();
//                })
//                .formLogin().disable()
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(customAuthenticationProvider)
//                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(customAccessDeniedHandler)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//
//                )
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry -> {
                    // Publicly accessible resources (e.g., register)
                    registry.requestMatchers("/register", "/login").permitAll();

                    // Admin specific resources (only accessible by admins)
                    registry.requestMatchers("/admin/**").hasRole("admin");

                    // User and admin resources (users and admins can access these)
                    registry.requestMatchers("/user/**").hasAnyRole("admin", "user");

                    // Organizer specific resources (only accessible by organizers)
                    registry.requestMatchers("/organizer/**").hasRole("organizer");

                    // Everything else needs to be authenticated
                    registry.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());  // The updated method for configuring JWT
        return http.build();
    }





}
