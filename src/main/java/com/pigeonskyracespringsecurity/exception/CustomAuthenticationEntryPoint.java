package com.pigeonskyracespringsecurity.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigeonskyracespringsecurity.DTO.ErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorDTO errorDTO = new ErrorDTO(
                "Unauthorized: " + authException.getMessage(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        // Serialize ErrorDTO to JSON and write it to the response
        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    }
}