package com.example.productservice.dto.product;

import com.example.commonlib.enums.CategoryEnum;
import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ProductUpdateRequest(

        @Size(max = 100, message = "Product name must not exceed 100 characters")
        String name,

        CategoryEnum categoryEnum,

        UUID brand_id,

        @Size(max = 5000, message = "Ingredients text too long")
        String ingredients,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        List<SkinTypeEnum> skinTypeEnum,

        List<ConcernTypeEnum> concernTypeEnum,

        String imageUrl,

        @Min(value = 1000, message = "Minimum price is 1000")
        Long price
) {}