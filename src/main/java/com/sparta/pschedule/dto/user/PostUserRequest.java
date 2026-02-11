package com.sparta.pschedule.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRequest {

    @NotBlank(message = "유저이름은 필수입니다.")
    @Size(max = 4, message = "유저명은 4글자 이내입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
}
