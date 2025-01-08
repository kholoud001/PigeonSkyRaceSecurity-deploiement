package com.pigeonskyracespringsecurity.controller;
import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.mapper.PigeonMapper;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;


import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.service.CompetitionService;
import com.pigeonskyracespringsecurity.service.PigeonService;
import com.pigeonskyracespringsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class PigeonController {
    private final PigeonService pigeonService;
    private final UserService userService;



    @PostMapping("/add/pigeons")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addPigeon(@Valid @RequestBody PigeonDTO pigeonDTO, @RequestParam String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            pigeonDTO.setUserId(user.getId());

            PigeonDTO savedPigeonDTO = pigeonService.addPigeon(pigeonDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPigeonDTO);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/update/pigeon/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PigeonDTO> updatePigeon(@PathVariable Long id, @RequestBody PigeonDTO pigeonDTO) {
        PigeonDTO updatedPigeon = pigeonService.updatePigeon(id, pigeonDTO);
        return ResponseEntity.ok(updatedPigeon);
    }

    @DeleteMapping("/delete/pigeon/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deletePigeon(@PathVariable Long id) {
        pigeonService.deletePigeon(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pigeon/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PigeonDTO> getPigeonById(@PathVariable Long id) {
        PigeonDTO pigeon = pigeonService.getPigeonById(id);
        return ResponseEntity.ok(pigeon);
    }

    @GetMapping("/pigeons")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<PigeonDTO>> getAllPigeons() {
        List<PigeonDTO> pigeons = pigeonService.getAllPigeons();
        return ResponseEntity.ok(pigeons);
    }




}
