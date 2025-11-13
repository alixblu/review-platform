package com.example.postservice.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CommentCreationRequest(
        @NotNull(message = "User ID must not be null")
        UUID userId,

        @NotEmpty(message = "Content must not be empty")
        String content
        // postId sẽ được lấy từ URL path, không cần đưa vào body
) {}