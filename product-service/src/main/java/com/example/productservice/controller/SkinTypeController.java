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
public class SkinTypeController {

    private final SkinTypeService skinTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SkinTypeResponse>>> getAllSkinTypes() {
        List<SkinTypeResponse> skinTypes = skinTypeService.getAllSkinTypes();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all skin types", skinTypes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkinTypeResponse>> updateSkinType(
            @PathVariable UUID id,
            @RequestBody @Valid SkinTypeUpdateRequest request
    ) {
        SkinTypeResponse updated = skinTypeService.updateSkinType(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Skin type updated successfully", updated));
    }
}
