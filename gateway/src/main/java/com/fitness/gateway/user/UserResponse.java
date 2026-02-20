package com.fitness.gateway.user;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;

    private String keyClockId;

    private String email;

    private String firstName;

    private String lastName;

    private com.fitness.gateway.user.UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
