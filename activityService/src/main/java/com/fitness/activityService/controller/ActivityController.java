package com.fitness.activityService.controller;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.dto.ResponseDTO;
import com.fitness.activityService.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/track")
    public ResponseEntity<ResponseDTO> trackActivity(@RequestBody ActivityRequest request){

        ActivityResponse activityResponse = activityService.track(request);
        ResponseDTO newResponse=new ResponseDTO();
        newResponse.setMessage("Activity Added SuccessFully...");
        newResponse.setStatusCode(HttpStatus.OK);
        newResponse.setData(activityResponse);
        return ResponseEntity.ok(newResponse);
    }
}
