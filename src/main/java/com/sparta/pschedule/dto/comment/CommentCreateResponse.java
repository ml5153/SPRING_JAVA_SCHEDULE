package com.sparta.pschedule.dto.comment;

import com.sparta.pschedule.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentCreateResponse {
    private final Long id;
    private final String contents;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static CommentCreateResponse from(Comment comment) {
        return new CommentCreateResponse(
                comment.getId(),
                comment.getContents(),
                comment.getUser().getName(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
