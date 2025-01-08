package com.pigeonskyracespringsecurity.controller;


import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.service.ColombierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ColombierController {


    private final ColombierService colombierService;

    @PostMapping("/add/colombier")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ColombierDTO> createColombier(@RequestBody ColombierDTO colombierDTO) {
        return ResponseEntity.ok(colombierService.createColombier(colombierDTO));
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
    public ResponseEntity<Void> deleteColombier(@PathVariable Long id) {
        colombierService.deleteColombier(id);
        return ResponseEntity.noContent().build();
    }
}
