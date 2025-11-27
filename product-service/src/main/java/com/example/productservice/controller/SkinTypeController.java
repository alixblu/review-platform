package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.skintype.SkinTypeResponse;
import com.example.productservice.dto.skintype.SkinTypeUpdateRequest;
import com.example.productservice.service.SkinTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skintype")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SkinTypeController {

    private final SkinTypeService skinTypeService;

    // ✅ GET all
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SkinTypeResponse>>> getAllSkinTypes() {
        List<SkinTypeResponse> response = skinTypeService.getAllSkinTypes();
        return ResponseEntity.ok(new ApiResponse<>("Fetched all skin types", response));
    }

    // ✅ GET by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkinTypeResponse>> getSkinTypeById(@PathVariable UUID id) {
        SkinTypeResponse response = skinTypeService.getSkinTypeById(id);
        return ResponseEntity.ok(new ApiResponse<>("Fetched skin type by id", response));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkinTypeResponse>> updateSkinType(
            @PathVariable UUID id,
            @Valid @RequestBody SkinTypeUpdateRequest request
    ) {
        SkinTypeResponse response = skinTypeService.updateSkinType(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Skin type updated successfully", response));
    }
}
