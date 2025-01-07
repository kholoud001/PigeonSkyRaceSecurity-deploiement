package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.model.entity.Result;
import com.pigeonskyracespringsecurity.repository.ResultRepository;
import com.pigeonskyracespringsecurity.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ResultServiceImpl implements ResultService {


    private final ResultRepository resultRepository;

    @Override
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }
    @Override
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
