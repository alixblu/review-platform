package com.example.productservice.dto.review;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewCreationRequest(
        @NotNull(message = "User ID must not be null")
        UUID userId,

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must not exceed 5")
        int rating,

        @NotEmpty(message = "Description must not be empty")
        String description,

        String imgUrl
) {}
