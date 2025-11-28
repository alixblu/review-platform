package com.example.postservice.dto.post;

import com.example.postservice.model.Post;
import com.pgvector.PGvector;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostResponse(
        Long id,
        UUID userId,
        UUID productId,
        String content,
        Post.Status status,
        LocalDateTime createAt,
        List<String> mediaUrls,
        int likeCount
//        PGvector embedding
) {}
