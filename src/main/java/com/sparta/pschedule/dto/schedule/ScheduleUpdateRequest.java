package com.sparta.pschedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequest {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    @NotBlank(message = "작성자 이름은 필수입니다.")
    private String author;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}