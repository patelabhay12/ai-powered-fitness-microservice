package com.fitness.aiService.services;


import com.fitness.aiService.models.Activity;
import com.fitness.aiService.models.Recommendation;
import com.fitness.aiService.repository.RecommendationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;

    private final RecommendationsRepository recommendationsRepository;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processActivity(Activity activity) {
        log.info("Received Activity for procession: {}", activity.getUserId());
        Recommendation recommendation = activityAiService.generateRecommendation(activity);
        recommendationsRepository.save(recommendation);
    }
}
