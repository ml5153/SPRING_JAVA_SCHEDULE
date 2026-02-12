package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.schedule.*;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<ScheduleCreateResponse> createSchedule(
            @Valid @RequestBody ScheduleCreateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = checkSession(httpRequest);
        ScheduleCreateResponse response = service.createSchedule(request, loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        // 조회의 id비교가 필요할지는 비즈니스에 따라 달라짐
        checkSession(httpRequest);
        ScheduleGetResponse response = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ScheduleGetResponse>> getSchedules(
            @RequestParam(required = false) String author,
            HttpServletRequest httpRequest
    ) {
        // 조회의 id비교가 필요할지는 비즈니스에 따라 달라짐
        checkSession(httpRequest);
        List<ScheduleGetResponse> responses = service.findAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = checkSession(httpRequest);
        ScheduleUpdateResponse response = service.update(id, request, loginUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDeleteRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = checkSession(httpRequest);
        service.delete(id, request, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private Long checkSession(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new CommonException(CommonError.UNAUTHORIZED_ACCESS);
        }
        return (Long) session.getAttribute("LOGIN_USER");
    }

}
