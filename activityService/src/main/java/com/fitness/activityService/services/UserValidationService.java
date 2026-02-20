package com.fitness.activityService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiveWebClient;

    public boolean validate(String userId){
        return Boolean.TRUE.equals(userServiveWebClient.get().uri("/api/users/{userId}/validate", userId).retrieve().bodyToMono(Boolean.class).block());
    }
}
