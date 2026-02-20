package com.fitness.aiService.services;


import com.fitness.aiService.models.Recommendation;
import com.fitness.aiService.repository.RecommendationsRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationsRepository recommendationsRepository;

    public @Nullable List<Recommendation> getUserRecommendation(String userId) {
        return recommendationsRepository.findByUserId(userId);
    }

    public @Nullable Recommendation getActivityRecommendation(String activityId) {
        return recommendationsRepository.findByActivityId(activityId).orElseThrow(() -> new RuntimeException("No Recommendation found with this activityId"));
    }
}
