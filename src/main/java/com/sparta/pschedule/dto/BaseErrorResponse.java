package com.sparta.pschedule.dto;

public record BaseErrorResponse(
        int status,
        String errorMessage
) {
}
