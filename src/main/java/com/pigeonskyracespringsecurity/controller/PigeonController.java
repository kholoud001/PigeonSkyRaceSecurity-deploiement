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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class PigeonController {
    private final PigeonService pigeonService;
    private final UserService userService;



    @PostMapping(("/add/pigeons/competition"))
    public ResponseEntity<PigeonDTO> addPigeonToCompetition(@Valid @RequestBody PigeonDTO pigeonDTO) {
        try {
            PigeonDTO responseDTO = pigeonService.addPigeonToCompetition(pigeonDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add/pigeons")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addPigeon(@Valid @RequestBody PigeonDTO pigeonDTO, @RequestParam String username) {
        // Find the user by username (you already have this part)
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the userId in the pigeonDTO before passing it to the service
        pigeonDTO.setUserId(user.getId());

        // Call the service method to add the pigeon
        PigeonDTO savedPigeonDTO = pigeonService.addPigeon(pigeonDTO);

        // Return the saved pigeon as a response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPigeonDTO);
    }


}
