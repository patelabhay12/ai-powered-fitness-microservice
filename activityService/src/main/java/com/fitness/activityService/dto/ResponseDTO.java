package com.fitness.activityService.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO <T>{

    private String message;

    private HttpStatus statusCode;

    private T data;
}
