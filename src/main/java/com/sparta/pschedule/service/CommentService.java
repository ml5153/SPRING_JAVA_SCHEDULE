package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.comment.GetCommentResponse;
import com.sparta.pschedule.dto.comment.PostCommentRequest;
import com.sparta.pschedule.dto.comment.PostCommentResponse;
import com.sparta.pschedule.entity.Comment;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.extension.ValidationExtension;
import com.sparta.pschedule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    @Transactional
    public PostCommentResponse create(Long scheduleId, PostCommentRequest request) {
        long commentCount = repository.countByScheduleId(scheduleId);
        if (commentCount > 10) {
            throw new CommonException(CommonError.COMMENT_LIMIT_EXCEEDED);
        }

        ValidationExtension.validate(request.getContents(), 100, "댓글 내용은 100자내로 입력해주세요.");

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

    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(Long scheduleId) {
        List<Comment> comments = repository.findAllByScheduleId(scheduleId);

        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getScheduleId(),
                        comment.getContents(),
                        comment.getAuthor(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .toList();
    }
}
