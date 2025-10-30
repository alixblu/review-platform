package com.example.productservice.dto.analysis;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record AnalysisUpdateRequest (
    @NotNull(message = "Product ID must not be null")
    UUID productId,

    @NotNull(message = "High risk list must not be null")
    @Size(min = 1, message = "High risk list must contain at least one element")
    List<@NotEmpty(message = "High risk item cannot be empty") String> highRisk,

    @NotNull(message = "Average risk list must not be null")
    @Size(min = 1, message = "Average risk list must contain at least one element")
    List<@NotEmpty(message = "Average risk item cannot be empty") String> avgRisk,

    @NotNull(message = "Low risk list must not be null")
    @Size(min = 1, message = "Low risk list must contain at least one element")
    List<@NotEmpty(message = "Low risk item cannot be empty") String> lowRisk
) {}