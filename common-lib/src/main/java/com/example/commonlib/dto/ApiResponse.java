package com.example.commonlib.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder; // <-- Dòng này tạo ra hàm builder()
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // <--- Annotation này tự động tạo ra hàm ApiResponse.builder()
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default // Giữ giá trị mặc định khi dùng builder
    private int code = 1000;
    private String message;
    private T result;

    public ApiResponse(String message, T  result) {
        this.message = message;
        this.result = result;
    }
}
