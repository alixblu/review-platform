package com.example.productservice.mapper;

import com.example.productservice.dto.review.ReviewCreationRequest;
import com.example.productservice.dto.review.ReviewResponse;
import com.example.productservice.model.Review;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReviewMapper {

    // Chuyển từ DTO sang entity
    Review toModel(ReviewCreationRequest request);

    // Chuyển từ entity sang response
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "userId", source = "userId")
    ReviewResponse toResponse(Review review);
}
