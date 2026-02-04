package com.sparta.pschedule.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PostCommentRequest {
    private Long scheduleId;
    private String contents;
    private String author;
    private String password;
}
