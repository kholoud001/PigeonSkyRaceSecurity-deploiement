package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.service.CompetitionService;
import com.pigeonskyracespringsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizer")

public class CompetitionController {

    private final CompetitionService competitionService;
    private final UserService userService;

    

    @PostMapping("/competitions")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> createCompetition(
            @Valid @RequestBody CompetitionDTO competitionDTO,
            @RequestParam String username) {

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        competitionDTO.setUserId(user.getId());
        Competition competition = competitionService.createCompetition(competitionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(competition);
    }

}
