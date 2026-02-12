package com.sparta.pschedule.dto.schedule;

import com.sparta.pschedule.dto.comment.CommentGetResponse;
import com.sparta.pschedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScheduleGetResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentGetResponse> comments;

    public static ScheduleGetResponse from(Schedule schedule, List<CommentGetResponse> comments) {
        return new ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                comments
        );
    }
}
