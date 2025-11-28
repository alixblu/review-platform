package com.example.productservice.dto.product;


import com.example.commonlib.enums.CategoryEnum;
import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.UUID;

public record ProductCreationRequest(
        @NotEmpty(message = "Product name must not be empty")
        @Size(max = 100, message = "Product name must not exceed 100 characters")
        String name,

        @NotNull(message = "Category must be provided")
        CategoryEnum categoryEnum,

        @NotNull(message = "Empty brand ID")
        UUID brand_id,

        @NotEmpty(message = "Ingredients must not be empty")
        String ingredients,

        @NotNull(message = "Skin types must be provided")
        @Size(min = 1, message = "At least one skin type must be selected")
        //string instead of enum cus request could be in lower case (not an enum)
        List<SkinTypeEnum> skinTypeEnum,

        @NotNull(message = "Concern types must be provided")
        @Size(min = 1, message = "At least one concern type must be selected")
        List<ConcernTypeEnum> concernTypeEnum,

        @NotEmpty(message = "Description must not be empty")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        String imageUrl,

        @NotNull(message = "Price must not be null")
        @Min(value = 1000, message = "Minimum of price is 1000")
        Long price
) {}