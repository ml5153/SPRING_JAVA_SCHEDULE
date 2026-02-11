package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.schedule.*;
import com.sparta.pschedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService service;

    @PostMapping()
    public ResponseEntity<PostScheduleResponse> postSchedule(
            @Valid @RequestBody PostScheduleRequest request
    ) {
        PostScheduleResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetScheduleResponse> getSchedule(
            @PathVariable Long id
    ) {
        GetScheduleResponse response = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<GetScheduleResponse>> getScheduleList(
            @RequestParam(required = false) String author
    ) {
        List<GetScheduleResponse> responses = service.findAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        UpdateScheduleResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody DeleteScheduleRequest request
    ) {
        service.delete(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
