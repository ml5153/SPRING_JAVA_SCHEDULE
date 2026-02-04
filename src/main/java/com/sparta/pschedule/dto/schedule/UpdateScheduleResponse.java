package com.sparta.pschedule.dto.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// java 14미만: 기존클래스 + Lombok
// java 14이상: record (거의 코틀린 'data class' 와 유사해보임)
@Getter
@RequiredArgsConstructor
public class UpdateScheduleResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
