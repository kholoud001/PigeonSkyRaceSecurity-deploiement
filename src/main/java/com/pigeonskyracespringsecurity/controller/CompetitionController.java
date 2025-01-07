package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizer")

public class CompetitionController {

    private final CompetitionService competitionService;
    private final UserService userService;
    private final PigeonService pigeonService;
    private final PigeonMapper pigeonMapper;

    

    @PostMapping("/add/competition")
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

    @GetMapping("/competitions")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        List<Competition> competitions = competitionService.getAllCompetitions();
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/competition/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<Competition> getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(competition);
    }


    @DeleteMapping("/delete/competition/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/competition/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<Competition> updateCompetition(@PathVariable Long id, @Valid @RequestBody CompetitionDTO competitionDTO) {
        Competition updatedCompetition = competitionService.updateCompetition(id, competitionDTO);
        return ResponseEntity.ok(updatedCompetition);
    }


    @GetMapping("/competitions/search")
    public ResponseEntity<List<Competition>> searchCompetitionsByName(@RequestParam String name) {
        List<Competition> competitions = competitionService.searchCompetitionsByName(name);
        return ResponseEntity.ok(competitions);
    }


    @PostMapping("/add-to-competition")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> addPigeonsToCompetition(@RequestBody List<String> ringNumbers, @RequestParam Long competitionId) {
        try {
            Competition competition = competitionService.findById(competitionId)
                    .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

            List<Pigeon> pigeons = pigeonService.findByRingNumbers(ringNumbers);

            pigeons.forEach(pigeon -> pigeon.setCompetition(competition));

            pigeonService.saveAll(pigeons);

            // Convert to DTO and return the result
            List<PigeonDTO> pigeonDTOs = pigeons.stream()
                    .map(pigeonMapper::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(pigeonDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }





}
