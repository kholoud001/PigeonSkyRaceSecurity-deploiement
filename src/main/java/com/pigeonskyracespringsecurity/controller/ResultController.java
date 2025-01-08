package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.model.entity.Result;
import com.pigeonskyracespringsecurity.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizer/results")
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public Optional<Result> getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public Result createResult(@RequestBody Result result) {
        return resultService.saveResult(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public void deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
    }

    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @PostMapping("/uploadResults/{competitionId}")
    public ResponseEntity<String> uploadResults(@PathVariable Long competitionId, @RequestBody List<Result> results) {
        try {
            resultService.uploadResults(competitionId, results);
            return ResponseEntity.ok("Results uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
