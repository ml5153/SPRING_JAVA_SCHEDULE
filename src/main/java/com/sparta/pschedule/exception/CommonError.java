package com.sparta.pschedule.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonError {
    NO_FIND_SCHEDULE(HttpStatus.NOT_FOUND, "조회할 일정이 없습니다."),
    NO_UPDATE_SCHEDULE(HttpStatus.NOT_FOUND, "수정할 일정이 없습니다."),
    NO_DELETE_SCHEDULE(HttpStatus.NOT_FOUND, "삭제할 일정이 없습니다."),
    NO_FIND_USER(HttpStatus.NOT_FOUND, "조회할 유저가 없습니다."),
    NO_UPDATE_USER(HttpStatus.NOT_FOUND, "수정할 유저가 없습니다."),
    NO_DELETE_USER(HttpStatus.NOT_FOUND, "삭제할 유저가 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    COMMENT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "더이상 댓글을 달 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    /*
     messages.properties 를 써서 String 리소스를 분리할까 고민했다가 지웠다.
     (파일의 Depth가 길어져 더힘든듯하다.)
     */


}

