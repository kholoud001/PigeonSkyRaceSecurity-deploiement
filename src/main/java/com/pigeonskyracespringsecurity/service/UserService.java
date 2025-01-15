package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.exception.UsernameAlreadyExistsException;
import com.pigeonskyracespringsecurity.model.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    User register(UserDTO userDTO) throws UsernameAlreadyExistsException;


    @Transactional
    User register(UserDTO userDTO) throws UsernameAlreadyExistsException;

    User changeRole(String username, String newRole);

    User updateUser(Long userId, UserDTO userDTO);

    Optional<User> findByUsername(String name);

    List<User> findAllUsers();

    List<User> searchUsers(String searchTerm);

    void deleteUser(Long userId);

    List<User> getAllUsers();
}


