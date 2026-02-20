package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final WebClient userServiveWebClient;

    public Mono<Boolean> validateUser(String keyCloakId) {
        return userServiveWebClient.get().uri("/api/users/{keyCloakId}/validate", keyCloakId).retrieve().bodyToMono(Boolean.class).onErrorResume(WebClientResponseException.class, e -> {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return Mono.error(new RuntimeException("User Not Found : " + keyCloakId));

            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                return Mono.error(new RuntimeException("Invalid : " + keyCloakId));

            return Mono.error(new RuntimeException("Unexpected Error : " + keyCloakId));
        });
    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("Calling User Registration for {}", registerRequest.getEmail());

        return userServiveWebClient.post().uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve().bodyToMono(UserResponse.class).onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad Request : " + e.getMessage()));
                    return Mono.error(new RuntimeException("Unexpected Error : " + e.getMessage()));
                });
    }
}
