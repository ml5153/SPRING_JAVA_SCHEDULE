package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.comment.CommentCreateRequest;
import com.sparta.pschedule.dto.comment.CommentCreateResponse;
import com.sparta.pschedule.dto.comment.CommentGetResponse;
import com.sparta.pschedule.entity.Comment;
import com.sparta.pschedule.entity.Schedule;
import com.sparta.pschedule.entity.User;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.repository.CommentRepository;
import com.sparta.pschedule.repository.ScheduleRepository;
import com.sparta.pschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentCreateResponse create(Long loginUserId, Long scheduleId, CommentCreateRequest request) {
        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new CommonException(CommonError.NOT_FOUND_USER)
        );
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND_SCHEDULE));

        long commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount > 10) {
            throw new CommonException(CommonError.COMMENT_LIMIT_EXCEEDED);
        }

        Comment newComment = new Comment(request.getContents(), schedule, user);
        Comment comment = commentRepository.save(newComment);
        return CommentCreateResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentGetResponse> findAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);

        return comments.stream()
                .map(comment -> CommentGetResponse.from(comment))
                .toList();
    }
}
