package com.pigeonskyracespringsecurity.controller;


import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.service.ColombierService;
import com.pigeonskyracespringsecurity.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ColombierController {


    private final ColombierService colombierService;
    private final UserService userService;

    @PostMapping("/add/colombier")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Colombier> addColombier(@RequestBody Colombier colombier, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());

        Colombier createdColombier = colombierService.addColombier(userId, colombier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdColombier);
    }

    @GetMapping("/colombier/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ColombierDTO> getColombierById(@PathVariable Long id) {
        return ResponseEntity.ok(colombierService.getColombierById(id));
    }

    @PutMapping("/update/colombier/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ColombierDTO> updateColombier(@PathVariable Long id, @RequestBody ColombierDTO colombierDTO) {
        return ResponseEntity.ok(colombierService.updateColombier(id, colombierDTO));
    }

    @DeleteMapping("/delete/colombier/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteColombier(@PathVariable Long colombierId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());

        colombierService.deleteColombier(colombierId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-colombiers")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ColombierDTO>> getMyColombiers(@RequestParam String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            List<ColombierDTO> myColombiers = colombierService.getColombiersByUserId(user.getId());

            return ResponseEntity.ok(myColombiers);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
