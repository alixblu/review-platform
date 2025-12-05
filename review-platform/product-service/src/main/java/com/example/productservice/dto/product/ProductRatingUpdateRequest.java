package com.example.productservice.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ProductRatingUpdateRequest(

        @Min(value = 0, message = "Rating must be >= 0")
        @Max(value = 5, message = "Rating must be <= 5")
        Float rating
) {}
