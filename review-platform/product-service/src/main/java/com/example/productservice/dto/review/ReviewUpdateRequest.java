package com.example.productservice.dto.review;

import jakarta.validation.constraints.*;

public record ReviewUpdateRequest(

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must not exceed 5")
        Integer rating,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        @NotBlank(message = "Description cannot be blank")
        String description,

        @Size(max = 255, message = "Image URL must not exceed 255 characters")
        String imgUrl
) {}
