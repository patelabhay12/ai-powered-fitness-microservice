package com.fitness.aiService.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = "recommendations")
@Data
@Builder
public class Recommendation {

    @Id
    private String id;

    private String activityId;

    private String userId;

    private ActivityType type;

    private String recommendation;

    private List<String> improvements;

    private List<String> suggestions;

    private List<String> safety;

    @CreatedDate
    private LocalDateTime createdAt;
}
