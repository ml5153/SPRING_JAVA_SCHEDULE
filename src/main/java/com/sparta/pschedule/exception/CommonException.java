package com.sparta.pschedule.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final CommonError commonError;

    public CommonException(CommonError commonError) {
        super(commonError.getMessage());
        this.commonError = commonError;
    }
}
