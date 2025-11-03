package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.analysis.AnalysisResponse;
import com.example.productservice.dto.review.ReviewCreationRequest;
import com.example.productservice.dto.review.ReviewResponse;
import com.example.productservice.dto.review.ReviewUpdateRequest;
import com.example.productservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Create a new review for a product
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @RequestBody @Valid ReviewCreationRequest request) {

        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Review created successfully", response));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
        List<ReviewResponse> analyses = reviewService.getAllReviews();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all analyses", analyses));
    }
    /**
     * Update an existing review by ID
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody @Valid ReviewUpdateRequest request) {

        ReviewResponse response = reviewService.updateReview(reviewId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Review updated successfully", response));
    }

    /**
     * Get all reviews of a specific product
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByProduct(
            @PathVariable UUID productId) {

        List<ReviewResponse> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(
                new ApiResponse<>("Fetched reviews by product successfully", reviews)
        );
    }

    /**
     * Get all reviews created by a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByUser(
            @PathVariable String userId) {

        List<ReviewResponse> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(
                new ApiResponse<>("Fetched reviews by user successfully", reviews)
        );
    }
}
