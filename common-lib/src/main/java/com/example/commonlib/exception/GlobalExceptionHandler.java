package com.example.commonlib.exception;

import com.example.commonlib.dto.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
    @ExceptionHandler({IllegalArgumentException.class, org.springframework.http.converter.HttpMessageConversionException.class})
    public ResponseEntity<ApiResponse<?>> handleEnumParseError(Exception e) {
        ApiResponse<?> response = new ApiResponse<>(
                ErrorCode.INVALID_INPUT.getCode(),
                "Invalid enum value provided",
                null
        );
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus()).body(response);
    }

    // 🔹 Handle validation errors (e.g., @Valid)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation failed";
        ApiResponse<?> response = new ApiResponse<>(ErrorCode.INVALID_INPUT.getCode(), message, null);
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus()).body(response);
    }

    // 🔹 Catch-all: Handle ANY other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleUnexpectedException(Exception e) {
        // Optionally log the stacktrace
        e.printStackTrace();

        ApiResponse<?> response = new ApiResponse<>(
                ErrorCode.INTERNAL_ERROR.getCode(),
                "Unexpected error occurred",
                null
        );

        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getStatus()).body(response);
    }
}
