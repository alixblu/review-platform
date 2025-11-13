package com.example.postservice.dto.like;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

// Chỉ cần userId, vì postId sẽ lấy từ URL
public record PostLikeRequest(
        @NotNull(message = "User ID must not be null")
        UUID userId
) {}