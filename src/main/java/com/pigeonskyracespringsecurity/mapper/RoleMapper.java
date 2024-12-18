package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.RoleDTO;
import com.pigeonskyracespringsecurity.model.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

//    @Mapping(target = "userNames", expression = "java(role.getUsers().stream().map(User::getUsername).collect(Collectors.toSet()))")
//    RoleDTO toRoleDTO(Role role);
}
