package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "roleType")
    User toUser(UserDTO userDTO);

    @Mapping(target = "roleType", source = "role.roleType")
    UserDTO toUserDTO(User user);

}