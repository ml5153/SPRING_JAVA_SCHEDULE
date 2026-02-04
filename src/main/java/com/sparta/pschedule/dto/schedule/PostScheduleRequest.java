package com.sparta.pschedule.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// java 14미만: 기존클래스 + Lombok
// java 14이상: record (거의 코틀린 'data class' 와 유사해보임)
@Getter
@AllArgsConstructor
public class PostScheduleRequest {
    private String title;
    private String contents;
    private String author;
    private String password;
}
