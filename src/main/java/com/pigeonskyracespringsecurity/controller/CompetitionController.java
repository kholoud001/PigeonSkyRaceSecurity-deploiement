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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizer")

public class CompetitionController {

    private final CompetitionService competitionService;
    private final UserService userService;

    

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





}
