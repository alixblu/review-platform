package com.example.postservice.dto.comment;

import com.example.postservice.model.Comment; // <-- Thêm import
import jakarta.validation.constraints.NotEmpty;

public record CommentUpdateRequest(
        @NotEmpty(message = "Content must not be empty")
        String content,
        Comment.Status status
) {}