package com.sparta.pschedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostScheduleRequest {
    private String title;
    private String contents;
    private String author;
    private String password;
}
