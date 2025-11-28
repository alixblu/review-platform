package com.example.adminservice.client;

import com.example.adminservice.dto.request.ExternalEntityStatusUpdateRequest;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "user-service") // Tên service đăng ký trên Eureka
public interface UserClient {


    @PutMapping("/api/v1/users/{userId}/status")
    ApiResponse<?> updateUserStatus(
            @PathVariable("userId") UUID userId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request
    );
}