package com.sparta.pschedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetScheduleResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
