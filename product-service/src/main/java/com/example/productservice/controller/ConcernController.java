package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.dto.concern.ConcernUpdateRequest;
import com.example.productservice.service.ConcernService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/concern")
@RequiredArgsConstructor
public class ConcernController {

    private final ConcernService concernService;

    // ✅ GET all
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ConcernResponse>>> getAllConcerns() {
        List<ConcernResponse> response = concernService.getAllConcerns();
        return ResponseEntity.ok(new ApiResponse<>("Fetched all concerns", response));
    }

    // ✅ GET by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConcernResponse>> getConcernById(@PathVariable UUID id) {
        ConcernResponse response = concernService.getConcernById(id);
        return ResponseEntity.ok(new ApiResponse<>("Fetched concern by id", response));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ConcernResponse>> updateConcern(
            @PathVariable UUID id,
            @Valid @RequestBody ConcernUpdateRequest request
    ) {
        ConcernResponse response = concernService.updateConcern(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Concern updated successfully", response));
    }
}
