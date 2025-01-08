package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final CompetitionService competitionService;


    @GetMapping("/competitions/search")
    public ResponseEntity<List<Competition>> searchCompetitionsByName(@RequestParam String name) {
        List<Competition> competitions = competitionService.searchCompetitionsByName(name);
        return ResponseEntity.ok(competitions);
    }
}
