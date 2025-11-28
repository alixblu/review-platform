package com.example.postservice.dto.comment;

import com.example.postservice.model.Comment;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        Long id,
        String content,
        UUID userId,
        Long postId,
        LocalDateTime createAt,
        Comment.Status status
) {}