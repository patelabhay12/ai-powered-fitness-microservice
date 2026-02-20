package com.fitness.activityService.services;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.models.Activity;
import com.fitness.activityService.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final UserValidationService userValidationService;

    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private  String  EVENT_TOPIC;
    public ActivityResponse track(ActivityRequest request) {

        boolean isValidUser = userValidationService.validate(request.getUserId());

        if(!isValidUser){
            throw new RuntimeException("Invalid userId");
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .additionalMatrix(request.getAdditionalMatrix())
                .startTime(request.getStartTime())
                .additionalMatrix(request.getAdditionalMatrix())
                .caloriesBurned(request.getCaloriesBurned())
                .duration(request.getDuration())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            kafkaTemplate.send(EVENT_TOPIC,savedActivity.getUserId(),savedActivity);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ActivityResponse.builder()
                .id(savedActivity.getId())
                .userId(savedActivity.getUserId())
                .type(savedActivity.getType())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .additionalMatrix(savedActivity.getAdditionalMatrix())
                .duration(savedActivity.getDuration())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .startTime(savedActivity.getStartTime()).build();
    }
}
