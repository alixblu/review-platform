package com.example.postservice.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PostCreationRequest(
        @NotNull(message = "User ID must not be null")
        UUID userId,

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        @NotEmpty(message = "Content must not be empty")
        @Size(max = 1000, message = "Content must not exceed 1000 characters")
        String content,

        @Size(max = 3, message = "You can upload up to 3 media items")
        List<String> mediaList

) {}