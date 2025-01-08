package com.pigeonskyracespringsecurity.service;

import com.pigeonskyracespringsecurity.model.entity.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    List<Result> getAllResults();

    Optional<Result> getResultById(Long id);

    Result saveResult(Result result);

    void deleteResult(Long id);

    void uploadResults(Long competitionId, List<Result> results);
}
