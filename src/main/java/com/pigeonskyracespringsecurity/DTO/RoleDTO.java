package com.pigeonskyracespringsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;

    @NotBlank(message = "Role can't be blank")
    private String roleType;
    private List<UserDTO> users;
}
