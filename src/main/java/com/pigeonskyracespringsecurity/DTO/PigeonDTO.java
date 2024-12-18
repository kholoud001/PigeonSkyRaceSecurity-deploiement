package com.pigeonskyracespringsecurity.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PigeonDTO {
    @NotBlank(message = "ringNumber  must be entered")
    private String ringNumber;

    @NotBlank(message = "must enter whether MALE or FEMALE")
    private String gender;

    @Min(value = 1, message = "Pigeon age must be at least 1")
    private int age;

    @NotBlank(message = "Pigeon color  must be entered")
    private String color;

    private Long competitionId;

    private Long userId;

}
