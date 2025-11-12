package com.example.adminservice.client;

import com.example.adminservice.dto.request.ExternalEntityStatusUpdateRequest;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "post-service") // Tên service đăng ký trên Eureka
public interface PostClient {


    @PutMapping("/api/v1/posts/{postId}/status")
    ApiResponse<?> updatePostStatus(
            @PathVariable("postId") UUID postId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request
    );


    @PutMapping("/api/v1/comments/{commentId}/status")
    ApiResponse<?> updateCommentStatus(
            @PathVariable("commentId") UUID commentId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request
    );
}