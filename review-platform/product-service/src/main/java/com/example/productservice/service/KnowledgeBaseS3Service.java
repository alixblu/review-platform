package com.example.productservice.service;

import com.example.productservice.dto.knowledgebase.BrandKnowledgeBaseDto;
import com.example.productservice.dto.knowledgebase.ProductKnowledgeBaseDto;
import com.example.productservice.model.Brand;
import com.example.productservice.model.Product;
import com.example.productservice.repository.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KnowledgeBaseS3Service {

    private final S3Client knowledgeBaseS3Client;
    private final BrandRepository brandRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public KnowledgeBaseS3Service(@org.springframework.beans.factory.annotation.Qualifier("knowledgeBaseS3Client") S3Client knowledgeBaseS3Client,
                                   BrandRepository brandRepository) {
        this.knowledgeBaseS3Client = knowledgeBaseS3Client;
        this.brandRepository = brandRepository;
    }

    @Value("${s3.knowledgebase.bucket}")
    private String knowledgeBaseBucket;

    public void uploadProductJson(Product product) {
        try {
            // Get brand info
            Brand brand = brandRepository.findById(product.getBrand_id()).orElse(null);
            
            ProductKnowledgeBaseDto.BrandInfo brandInfo = brand != null
                    ? new ProductKnowledgeBaseDto.BrandInfo(
                            brand.getId().toString(),
                            brand.getName()
                    )
                    : new ProductKnowledgeBaseDto.BrandInfo(
                            product.getBrand_id().toString(),
                            "Unknown"
                    );

            // Parse ingredients
            List<String> ingredientsList = product.getIngredients() != null
                    ? Arrays.stream(product.getIngredients().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList())
                    : List.of();

            // Convert enums to strings
            List<String> concernTypes = product.getConcernTypeEnum() != null
                    ? product.getConcernTypeEnum().stream()
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .collect(Collectors.toList())
                    : List.of();

            List<String> skinTypes = product.getSkinTypeEnum() != null
                    ? product.getSkinTypeEnum().stream()
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .collect(Collectors.toList())
                    : List.of();

            ProductKnowledgeBaseDto dto = ProductKnowledgeBaseDto.builder()
                    .id(product.getId().toString())
                    .name(product.getName())
                    .brand(brandInfo)
                    .category(product.getCategoryEnum() != null ? product.getCategoryEnum().name().toLowerCase() : "")
                    .ingredients(ingredientsList)
                    .description(product.getDescription())
                    .concernType(concernTypes)
                    .skinType(skinTypes)
                    .imageUrl(product.getImageUrl())
                    .price(product.getPrice())
                    .rating(product.getRating())
                    .status(product.getStatus().name().toLowerCase())
                    .updatedAt(LocalDateTime.now())
                    .build();

            String jsonContent = objectMapper.writeValueAsString(dto);
            String key = "products/" + product.getId() + ".json";

            uploadJsonToS3(key, jsonContent);
            log.info("Uploaded product JSON to knowledge base: {}", key);
        } catch (Exception e) {
            log.error("Failed to upload product JSON to knowledge base", e);
        }
    }

    public void uploadBrandJson(Brand brand) {
        try {
            BrandKnowledgeBaseDto dto = BrandKnowledgeBaseDto.builder()
                    .id(brand.getId().toString())
                    .name(brand.getName())
                    .description(brand.getDescription())
                    .website(brand.getWebsite())
                    .country(brand.getCountry())
                    .status(brand.getStatus().name().toLowerCase())
                    .updatedAt(LocalDateTime.now())
                    .build();

            String jsonContent = objectMapper.writeValueAsString(dto);
            String key = "brands/" + brand.getId() + ".json";

            uploadJsonToS3(key, jsonContent);
            log.info("Uploaded brand JSON to knowledge base: {}", key);
        } catch (Exception e) {
            log.error("Failed to upload brand JSON to knowledge base", e);
        }
    }

    private void uploadJsonToS3(String key, String jsonContent) {
        log.info("Attempting to upload to S3: bucket={}, key={}", knowledgeBaseBucket, key);
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(knowledgeBaseBucket)
                .key(key)
                .contentType("application/json")
                .build();

        try {
            knowledgeBaseS3Client.putObject(putObjectRequest, RequestBody.fromString(jsonContent));
            log.info("Successfully uploaded to S3: {}", key);
        } catch (Exception e) {
            log.error("Failed to upload to S3. Bucket: {}, Key: {}, Error: {}", 
                    knowledgeBaseBucket, key, e.getMessage());
            throw e;
        }
    }

    public void deleteProductJson(String productId) {
        try {
            String key = "products/" + productId + ".json";
            deleteFromS3(key);
            log.info("Deleted product JSON from knowledge base: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete product JSON from knowledge base", e);
        }
    }

    public void deleteBrandJson(String brandId) {
        try {
            String key = "brands/" + brandId + ".json";
            deleteFromS3(key);
            log.info("Deleted brand JSON from knowledge base: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete brand JSON from knowledge base", e);
        }
    }

    private void deleteFromS3(String key) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(knowledgeBaseBucket)
                .key(key)
                .build();
        knowledgeBaseS3Client.deleteObject(deleteRequest);
    }
}
