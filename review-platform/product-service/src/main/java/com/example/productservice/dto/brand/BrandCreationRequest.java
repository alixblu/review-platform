package com.example.productservice.dto.brand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a new Brand.
 * Based on database structure of `brand` table.
 */
public record BrandCreationRequest(

        @NotEmpty(message = "Brand name must not be empty")
        @Size(max = 100, message = "Brand name must not exceed 100 characters")
        String name,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @Size(max = 255, message = "Website URL must not exceed 255 characters")
        String website,

        @Size(max = 255, message = "Logo URL must not exceed 255 characters")
        String logoUrl,

        @Size(max = 100, message = "Country name must not exceed 100 characters")
        String country
) {}