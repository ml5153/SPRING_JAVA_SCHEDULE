package com.sparta.pschedule.dto.schedule;

import java.time.LocalDateTime;

public record SchedulePageResponse(
        Long id,
        String title,
        String contents,
        String userName,
        Long commentCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt

) {
}