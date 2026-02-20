package com.fitness.activityService.dto;

import com.fitness.activityService.models.ActivityType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;


@Data
public class ActivityRequest {

    private String id;

    private String userId;

    private ActivityType type;

    private Integer duration;

    private Integer caloriesBurned;

    private LocalDateTime startTime;

    private Map<String,Object> additionalMatrix;
}
