package com.example.adminservice.client;

import com.example.adminservice.dto.request.ExternalEntityStatusUpdateRequest;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "product-service") // Tên service đăng ký trên Eureka
public interface ProductClient {


    @PutMapping("/api/v1/products/{productId}/status")
    ApiResponse<?> updateProductStatus(
            @PathVariable("productId") UUID productId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request
    );


    @PutMapping("/api/v1/reviews/{reviewId}/status")
    ApiResponse<?> updateReviewStatus(
            @PathVariable("reviewId") UUID reviewId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request
    );
}