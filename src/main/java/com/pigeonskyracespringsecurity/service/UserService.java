package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.exception.UsernameAlreadyExistsException;
import com.pigeonskyracespringsecurity.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(UserDTO userDTO) throws UsernameAlreadyExistsException;


    User changeRole(String username, String newRole);

    Optional<User> findByUsername(String name);

    List<User> findAllUsers();

    void deleteUser(Long userId);
}
