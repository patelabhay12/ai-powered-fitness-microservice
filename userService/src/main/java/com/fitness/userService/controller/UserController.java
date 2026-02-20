package com.fitness.userService.controller;


import com.fitness.userService.dto.ApiResponse;
import com.fitness.userService.dto.RegisterRequest;
import com.fitness.userService.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/test")
    public String test() {
        return "Service reached";
    }


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserProfile(@PathVariable String userId) {
        Object user = userService.getUserProfile(userId);
        ApiResponse<Object> response = new ApiResponse<>("User fetched successfully", 200, user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Object result = userService.register(request);
            ApiResponse<Object> response = new ApiResponse<>("User registered successfully", 200, result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(e.getMessage(), 400, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.existByUserId(userId));
    }


}
