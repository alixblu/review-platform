package com.example.adminservice.controller;

import com.example.adminservice.dto.request.ExternalEntityStatusUpdateRequest;
import com.example.adminservice.service.ManagementService;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class ManagementController {

    private final ManagementService managementService;

    @PutMapping("/products/{productId}/status")
    public ApiResponse<?> updateProductStatus(
            @PathVariable UUID productId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for product: {}", productId);
        return managementService.updateProductStatus(productId, request);
    }


    @PutMapping("/users/{userId}/status")
    public ApiResponse<?> updateUserStatus(
            @PathVariable UUID userId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for user: {}", userId);
        return managementService.updateUserStatus(userId, request);
    }


    @PutMapping("/posts/{postId}/status")
    public ApiResponse<?> updatePostStatus(
            @PathVariable UUID postId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for post: {}", postId);
        return managementService.updatePostStatus(postId, request);
    }

    @PutMapping("/reviews/{reviewId}/status")
    public ApiResponse<?> updateReviewStatus(
            @PathVariable UUID reviewId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for review: {}", reviewId);
        return managementService.updateReviewStatus(reviewId, request);
    }


    @PutMapping("/comments/{commentId}/status")
    public ApiResponse<?> updateCommentStatus(
            @PathVariable UUID commentId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for comment: {}", commentId);
        return managementService.updateCommentStatus(commentId, request);
    }

    @PutMapping("/accounts/{accountId}/status")
    public ApiResponse<?> updateAccountStatus(
            @PathVariable UUID accountId,
            @Valid @RequestBody ExternalEntityStatusUpdateRequest request) {
        log.info("Admin request to update status for account: {}", accountId);
        return managementService.updateAccountStatus(accountId, request);
    }
}