package com.example.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private String message;
    private ProductRecommendation recommendation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductRecommendation {
        private String query;
        private DetectedFilters detectedFilters;
        private List<RecommendedProduct> products;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DetectedFilters {
        private List<String> skinType;
        private List<String> concernType;
        private List<String> category;
        private PriceRange priceRange;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PriceRange {
        private Double min;
        private Double max;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecommendedProduct {
        private String id;
        private String name;
        private String brandName;
        private String category;
        private Double price;
        private Double rating;
        private String imageUrl;
        private List<String> skinType;
        private List<String> concernType;
        private String reason;
    }
}
