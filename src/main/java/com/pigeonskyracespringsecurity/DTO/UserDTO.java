package com.pigeonskyracespringsecurity.DTO;

import com.pigeonskyracespringsecurity.model.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "Username must be entered")
    private String username;

    @NotBlank(message = "Please provide a password")
    private String password;
    @NotNull
    private RoleType roleType;

    private List<CompetitionDTO> competitions;

}


