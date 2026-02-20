package com.fitness.userService.dto;

import com.fitness.userService.models.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;

    private String keyClockId;

    private String email;

    private String firstName;

    private String lastName;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
