package com.example.adminservice.service;

import com.example.adminservice.dto.request.ExternalEntityStatusUpdateRequest;
import com.example.commonlib.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.adminservice.client.AuthClient; // Thêm import
import com.example.adminservice.client.PostClient;
import com.example.adminservice.client.ProductClient;
import com.example.adminservice.client.UserClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagementService {

    private final ProductClient productClient;
    private final UserClient userClient;
    private final PostClient postClient;
    private final AuthClient authClient;

    public ApiResponse<?> updateProductStatus(UUID productId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for product: {}", productId);
        return productClient.updateProductStatus(productId, request);
    }

    public ApiResponse<?> updateUserStatus(UUID userId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for user: {}", userId);
        return userClient.updateUserStatus(userId, request);
    }

    public ApiResponse<?> updatePostStatus(UUID postId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for post: {}", postId);
        return postClient.updatePostStatus(postId, request);
    }

    public ApiResponse<?> updateReviewStatus(UUID reviewId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for review: {}", reviewId);
        return productClient.updateReviewStatus(reviewId, request);
    }

    public ApiResponse<?> updateCommentStatus(UUID commentId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for comment: {}", commentId);
        return postClient.updateCommentStatus(commentId, request);
    }


    public ApiResponse<?> updateAccountStatus(UUID accountId, ExternalEntityStatusUpdateRequest request) {
        log.info("Admin updating status for account: {}", accountId);
        return authClient.updateAccountStatus(accountId, request);
    }
}