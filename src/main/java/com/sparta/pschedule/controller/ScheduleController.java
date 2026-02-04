package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.schedule.*;
import com.sparta.pschedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * try-catch & wildCard
 * 문제점 1: 모든 컨트롤러 메서드에 try-catch가 들어가서 코드가 지저분해짐
 * 문제점 2: <?>를 쓰면 리턴 데이터가 성공 시와 실패 시에 서로 달라져서 클라이언트가 타입을 예측하기 어려움
 * 해결: @ExceptionHandler 사용
 */
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
    public ResponseEntity<List<GetScheduleResponse>> getScheduleList(@RequestParam(required = false) String author) {
        List<GetScheduleResponse> responses = service.findAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @RequestMapping(value = "/schedules/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        UpdateScheduleResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody DeleteScheduleRequest request) {
        service.delete(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
