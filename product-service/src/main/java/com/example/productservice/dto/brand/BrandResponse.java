package com.example.productservice.dto.brand;

import com.example.productservice.model.Status;

import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name,
        String description,
        String website,
        String logoUrl,
        String country,
        Status status
) {
}