package com.example.productservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.dto.analysis.AnalysisResponse;
import com.example.productservice.dto.review.ReviewCreationRequest;
import com.example.productservice.dto.review.ReviewResponse;
import com.example.productservice.dto.review.ReviewUpdateRequest;
import com.example.productservice.mapper.ReviewMapper;
import com.example.productservice.model.Analysis;
import com.example.productservice.model.Review;
import com.example.productservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    /**
     * Create a new review
     */
    public ReviewResponse createReview(ReviewCreationRequest request) {
        Review review = reviewMapper.toModel(request);
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        log.info("Created review successfully for product {}", review.getProductId());
        return reviewMapper.toResponse(review);
    }

    /**
     * Update existing review
     */
    public ReviewResponse updateReview(UUID reviewId, ReviewUpdateRequest request) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Review not found"));

        if (request.description() != null) {
            existingReview.setDescription(request.description());
        }
        if (request.rating() != null) {
            existingReview.setRating(request.rating());
        }
        if (request.imgUrl() != null) {
            existingReview.setImgUrl(request.imgUrl());
        }

        reviewRepository.save(existingReview);
        log.info("Updated review {}", reviewId);
        return reviewMapper.toResponse(existingReview);
    }

    /**
     * Get all reviews by product ID
     */
    public List<ReviewResponse> getReviewsByProduct(UUID productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
    public List<ReviewResponse> getAllReviews() {
        List<Review> analyses = reviewRepository.findAll();
        return analyses.stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
    /**
     * Get all reviews by user ID
     */
    public List<ReviewResponse> getReviewsByUserId(String userId) {
        UUID userUUID = UUID.fromString(userId);
        List<Review> reviews = reviewRepository.findByUserId(userUUID);
        return reviews.stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}
