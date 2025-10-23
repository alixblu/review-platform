package com.example.postservice.dto;

import com.example.postservice.model.Post;
import com.pgvector.PGvector;

import java.util.List;
import java.util.UUID;

public record PostRequest(
        UUID userId,
        UUID productId,
        String content,
        List<String> mediaList,
        PGvector embedding
) {}
