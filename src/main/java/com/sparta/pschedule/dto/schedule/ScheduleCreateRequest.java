package com.sparta.pschedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequest {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(max = 10, message = "할일 제목은 10글자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    @NotBlank(message = "작성자명은 필수 입력 값입니다.")
    @Size(max = 4, message = "작성자명은 4글자 이내여야 합니다.")
    private String author;
}
