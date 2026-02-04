package com.sparta.pschedule.exception;

import com.sparta.pschedule.dto.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

// 모든 컨트롤러의 예외를 가로챈다.
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400: Bad Request (검증 실패 등)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseErrorResponse> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * 404: Not Found (데이터 없음)
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<BaseErrorResponse> handleNotFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    /**
     * 500: Internal Server Error (그 외 모든 에러)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponse> handleServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}