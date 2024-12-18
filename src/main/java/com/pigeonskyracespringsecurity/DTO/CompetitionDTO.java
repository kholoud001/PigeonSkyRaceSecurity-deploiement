package com.pigeonskyracespringsecurity.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionDTO {
    @NotBlank(message = "Competition name must be entered")
    private String name;

    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private double longitude;

    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @Min(value = 1, message = "Pigeon count must be at least 1")
    private int pigeonCount;

    @Min(value = 0, message = "Percentage must be at least 0")
    @Max(value = 100, message = "Percentage cannot exceed 100")
    private int percentage;

    @NotBlank(message = "Release place must be entered")
    private String releasePlace;

    private Long userId;
}
