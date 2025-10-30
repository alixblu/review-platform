package com.example.productservice.dto.analysis;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record AnalysisCreationRequest(

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        @NotNull(message = "High risk list must not be null")
        @Size(min = 1, message = "High risk list must contain at least one element")
        List<
                @NotBlank(message = "High risk item cannot be blank")
                @Size(max = 100, message = "High risk item must not exceed 100 characters")
                        String
                > highRisk,

        @NotNull(message = "Average risk list must not be null")
        @Size(min = 1, message = "Average risk list must contain at least one element")
        List<
                @NotBlank(message = "Average risk item cannot be blank")
                @Size(max = 100, message = "Average risk item must not exceed 100 characters")
                        String
                > avgRisk,

        @NotNull(message = "Low risk list must not be null")
        @Size(min = 1, message = "Low risk list must contain at least one element")
        List<
                @NotBlank(message = "Low risk item cannot be blank")
                @Size(max = 100, message = "Low risk item must not exceed 100 characters")
                        String
                > lowRisk
) {}
