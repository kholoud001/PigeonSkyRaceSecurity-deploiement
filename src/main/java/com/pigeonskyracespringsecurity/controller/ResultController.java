package com.pigeonskyracespringsecurity.controller;

import com.pigeonskyracespringsecurity.model.entity.Result;
import com.pigeonskyracespringsecurity.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public Optional<Result> getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PostMapping
    public Result createResult(@RequestBody Result result) {
        return resultService.saveResult(result);
    }

    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
    }
}
