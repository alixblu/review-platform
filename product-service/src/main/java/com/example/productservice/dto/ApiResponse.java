package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code = 1000;
    private String message;
    private T result;

    public ApiResponse( String message, T  result) {
        this.message = message;
        this.result = result;
    }
}
