package com.example.productservice.dto.knowledgebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BrandKnowledgeBaseDto(
        String id,
        String name,
        String description,
        String website,
        String country,
        String status,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {}
