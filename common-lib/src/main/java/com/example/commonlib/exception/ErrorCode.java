package com.example.commonlib.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_INPUT(1001, "Invalid input", HttpStatus.BAD_REQUEST),
    EXISTED(1002, "Already existed", HttpStatus.CONFLICT),
    NOT_FOUND(1003, "Not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1004, "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1005, "Forbidden action", HttpStatus.FORBIDDEN),
    INTERNAL_ERROR(1006, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
