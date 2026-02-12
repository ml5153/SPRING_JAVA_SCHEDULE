package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.comment.CommentCreateRequest;
import com.sparta.pschedule.dto.comment.CommentCreateResponse;
import com.sparta.pschedule.dto.comment.CommentGetResponse;
import com.sparta.pschedule.extension.AuthExtension;
import com.sparta.pschedule.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;


    @PostMapping()
    public ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long loginUserId = AuthExtension.checkSession(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(loginUserId, scheduleId, request));
    }


    @GetMapping()
    public ResponseEntity<List<CommentGetResponse>> getComments(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest
    ) {
        AuthExtension.checkSession(httpRequest);
        return ResponseEntity.ok(service.findAll(scheduleId));
    }
}
