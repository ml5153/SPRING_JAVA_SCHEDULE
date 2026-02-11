package com.sparta.pschedule.exception;

import com.sparta.pschedule.dto.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Validation Error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponse> handleValidError(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }


    /**
     * Custom Error
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<BaseErrorResponse> handleBusinessException(CommonException e) {
        CommonError errorCode = e.getCommonError();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new BaseErrorResponse(errorCode.getStatus().value(), errorCode.getMessage()));
    }
}