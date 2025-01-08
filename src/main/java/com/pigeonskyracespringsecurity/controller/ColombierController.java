package com.pigeonskyracespringsecurity.controller;


import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import com.pigeonskyracespringsecurity.service.ColombierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ColombierController {


    private final ColombierService colombierService;

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

}
