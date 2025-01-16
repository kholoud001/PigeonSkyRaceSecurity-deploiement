package com.pigeonskyracespringsecurity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDisplayDTO {
    private Long id;

    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RoleType roleType;

    private String colombierName;
}
