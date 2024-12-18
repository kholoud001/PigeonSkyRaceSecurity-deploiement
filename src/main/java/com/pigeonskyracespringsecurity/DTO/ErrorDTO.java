package com.pigeonskyracespringsecurity.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message;
    private String path;
   // private LocalDateTime timestamp;
    private int status;
}
