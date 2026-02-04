package com.sparta.pschedule.extension;

public class ValidationExtension {

    public static void validate(String value, String errorMsg) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void validate(String value, int max, String errorMsg) {
        validate(value, errorMsg);

        if (value.length() > max) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
