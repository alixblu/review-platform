package com.example.productservice.dto.review;

import com.example.productservice.model.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID userId,
        UUID productId,
        int rating,
        String description,
        String imgUrl,
        LocalDateTime createdAt,
        Status status
) {}
