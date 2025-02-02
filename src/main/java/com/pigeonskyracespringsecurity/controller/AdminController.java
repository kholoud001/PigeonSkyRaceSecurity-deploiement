package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.DTO.UserDisplayDTO;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDisplayDTO>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.noContent().build(); // Handle case where no users are found
            }
            List<UserDisplayDTO> userDisplayDTOs = users.stream()
                    .map(user -> new UserDisplayDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getRole().getRoleType(),
                            user.getColombier() != null ? user.getColombier().getName() : "Unknown"))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userDisplayDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }



    @Secured("ROLE_ADMIN")
    @PostMapping("/changeUserRole")
    public String changeUserRole(@RequestParam String username, @RequestParam String newRole) {
        try {
            userService.changeRole(username, newRole);
            return "User role updated successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(userId, userDTO);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/searchUsers")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String searchTerm) {
        List<User> users = userService.searchUsers(searchTerm);
        return ResponseEntity.ok(users);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/manage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> manageAdmins() {
        return ResponseEntity.ok("Welcome Admin! You have access to this endpoint.");
    }
}
