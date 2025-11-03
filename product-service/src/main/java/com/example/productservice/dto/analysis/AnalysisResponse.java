package com.example.productservice.dto.analysis;


import com.example.commonlib.enums.CategoryEnum;
import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import com.example.productservice.model.Status;

import java.util.List;
import java.util.UUID;

public record AnalysisResponse(
        UUID id,
        UUID productId,
        List<String> highRisk,
        List<String> avgRisk,
        List<String> lowRisk
) {}