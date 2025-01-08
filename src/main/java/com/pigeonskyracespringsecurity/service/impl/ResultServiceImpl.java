package com.pigeonskyracespringsecurity.service.impl;

import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import com.pigeonskyracespringsecurity.model.entity.Result;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.repository.CompetitionRepository;
import com.pigeonskyracespringsecurity.repository.PigeonRepository;
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
    private final CompetitionRepository competitionRepository;
    private final PigeonRepository pigeonRepository;


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

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Return distance in kilometers
    }

    @Override
    public void uploadResults(Long competitionId, List<Result> results) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        for (Result result : results) {
            Pigeon pigeon = result.getPigeon();
            // Calculate distance using Haversine formula
            double distance = haversine(competition.getLatitude(), competition.getLongitude(),
                    pigeon.getColombier().getLatitude(), pigeon.getColombier().getLongitude());

            result.setDistance(distance);

            double time = result.getTime(); // Assume `result.getTime()` is in minutes
            double speed = distance / time; // Speed in km/min

            result.setTime(time);
            result.setRank(calculateRank(results));

            resultRepository.save(result);
        }
    }

    private int calculateRank(List<Result> results) {
        results.sort((r1, r2) -> Double.compare(r2.getTime(), r1.getTime()));
        int rank = 1;
        for (Result result : results) {
            result.setRank(rank++);
        }
        return results.get(0).getRank();
    }

    private int calculatePerformancePoints(int rank, int totalParticipants) {
        // Points can be calculated based on rank (first gets the most points, etc.)
        // For example, if there are 10 participants, the first gets 10 points, the second gets 9, and so on
        return totalParticipants - rank + 1;
    }

}
