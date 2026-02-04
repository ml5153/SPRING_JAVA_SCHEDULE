package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.comment.GetCommentResponse;
import com.sparta.pschedule.dto.comment.PostCommentRequest;
import com.sparta.pschedule.dto.comment.PostCommentResponse;
import com.sparta.pschedule.entity.Comment;
import com.sparta.pschedule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    @Transactional
    public PostCommentResponse create(Long scheduleId, PostCommentRequest request) {
        long commentCount = repository.countByScheduleId(scheduleId);
        if(commentCount > 10) {
            throw new IllegalArgumentException("더이상 댓글을 달 수 없습니다.");
        }

        Comment newComment = new Comment(
                scheduleId,
                request.getContents(),
                request.getAuthor(),
                request.getPassword()
        );
        Comment comment = repository.save(newComment);
        return new PostCommentResponse(
                comment.getId(),
                comment.getScheduleId(),
                comment.getContents(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public GetCommentResponse findById(Long id) {
        Comment comment = repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당되는 댓글이 없습니다.")
        );
        return new GetCommentResponse(
                comment.getId(),
                comment.getScheduleId(),
                comment.getContents(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
