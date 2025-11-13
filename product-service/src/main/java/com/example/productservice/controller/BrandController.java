package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.brand.BrandCreationRequest;
import com.example.productservice.dto.brand.BrandResponse;
import com.example.productservice.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.productservice.dto.brand.BrandUpdateRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    /**
     * Tạo mới một thương hiệu
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@RequestBody @Valid BrandCreationRequest brand) {
        BrandResponse response = brandService.createBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Brand created successfully", response));
    }

    /**
     * Lấy danh sách tất cả thương hiệu
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        List<BrandResponse> brands = brandService.getAllBrands();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all brands", brands));
    }

    /**
     * Lấy thông tin chi tiết của một thương hiệu theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable UUID id) {
        BrandResponse brand = brandService.getBrandById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched brand successfully", brand));
    }

    /**
     * Cập nhật thông tin thương hiệu
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable UUID id,
            @RequestBody @Valid BrandCreationRequest request) {
        BrandResponse updated = brandService.updateBrand(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Brand updated successfully", updated));
    }

    /**
     * Ẩn hoặc xóa một thương hiệu
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Brand deleted successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable UUID id,
            @RequestBody @Valid BrandUpdateRequest request) {
        BrandResponse updated = brandService.updateBrand(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Brand updated successfully", updated));
    }
}