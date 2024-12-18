package com.pigeonskyracespringsecurity.exception;

import com.pigeonskyracespringsecurity.DTO.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleInvalidInput(IllegalArgumentException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleInvalidInput(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                request.getRequestURI(),
                //LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorDTO> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
//        ErrorDTO error = new ErrorDTO(
//                ex.getMessage(),
//                request.getRequestURI(),
//                HttpStatus.UNAUTHORIZED.value()
//        );
//        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ErrorDTO> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
//        ErrorDTO error = new ErrorDTO(
//                "Invalid username or password", // Custom message for clarity
//                request.getRequestURI(),
//                HttpStatus.UNAUTHORIZED.value()
//        );
//        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}
