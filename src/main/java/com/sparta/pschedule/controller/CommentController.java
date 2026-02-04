package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.comment.GetCommentResponse;
import com.sparta.pschedule.dto.comment.PostCommentRequest;
import com.sparta.pschedule.dto.comment.PostCommentResponse;
import com.sparta.pschedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;


    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<PostCommentResponse> postComment(
            @PathVariable Long scheduleId,
            @RequestBody PostCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(scheduleId, request));
    }


    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(service.findAll(scheduleId));
    }
}
