package com.fitness.activityService.dto;

import com.fitness.activityService.models.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
public class ActivityResponse {

    private String id;

    private String userId;

    private ActivityType type;

    private Integer duration;

    private Integer caloriesBurned;

    private LocalDateTime startTime;

    private Map<String,Object> additionalMatrix;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
