package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.*;
import com.sparta.pschedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService service;


    @PostMapping("/schedules")
    public ResponseEntity<PostScheduleResponse> postSchedule(@RequestBody PostScheduleRequest request) {
        PostScheduleResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long id) {
        GetScheduleResponse response = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getScheduleList() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @RequestMapping(value = "/schedules/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
