package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.schedule.*;
import com.sparta.pschedule.extension.AuthExtension;
import com.sparta.pschedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService service;

    @PostMapping()
    public ResponseEntity<ScheduleCreateResponse> createSchedule(
            @Valid @RequestBody ScheduleCreateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = AuthExtension.checkSession(httpRequest);
        ScheduleCreateResponse response = service.createSchedule(request, loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        AuthExtension.checkSession(httpRequest);
        ScheduleGetResponse response = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<Page<SchedulePageResponse>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest httpRequest
    ) {
        AuthExtension.checkSession(httpRequest);
        Page<SchedulePageResponse> result = service.getSchedulePage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = AuthExtension.checkSession(httpRequest);
        ScheduleUpdateResponse response = service.update(id, request, loginUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = AuthExtension.checkSession(httpRequest);
        service.delete(id, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
