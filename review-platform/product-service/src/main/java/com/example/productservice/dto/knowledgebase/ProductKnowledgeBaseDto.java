package com.example.productservice.dto.knowledgebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductKnowledgeBaseDto(
        String id,
        String name,
        BrandInfo brand,
        String category,
        List<String> ingredients,
        String description,
        @JsonProperty("concern_type") List<String> concernType,
        @JsonProperty("skin_type") List<String> skinType,
        @JsonProperty("image_url") String imageUrl,
        long price,
        float rating,
        String status,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
    @Builder
    public record BrandInfo(
            String id,
            String name
    ) {}
}
