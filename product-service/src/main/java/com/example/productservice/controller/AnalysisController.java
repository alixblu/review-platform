package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.analysis.AnalysisCreationRequest;
import com.example.productservice.dto.analysis.AnalysisResponse;
import com.example.productservice.service.AnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    /**
     * Create a new analysis record for a given product
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AnalysisResponse>> createAnalysis(
            @RequestBody @Valid AnalysisCreationRequest request) {

        AnalysisResponse response = analysisService.createAnalysis(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Analysis created successfully", response));
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<AnalysisResponse>> updateAnalysis(
            @PathVariable UUID productId,
            @RequestBody @Valid AnalysisCreationRequest request) {

        AnalysisResponse response = analysisService.updateAnalysis(productId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Analysis updated successfully", response));
    }
    /**
     * Get all analysis records
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AnalysisResponse>>> getAllAnalyses() {
        List<AnalysisResponse> analyses = analysisService.getAllAnalyses();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all analyses", analyses));
    }

    /**
     * Get analysis by product ID
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<AnalysisResponse>> getAnalysisByProductId(
            @PathVariable UUID productId) {

        AnalysisResponse response = analysisService.getAnalysisByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched analysis for product successfully", response));
    }
}
