package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.productservice.dto.product.*;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.productservice.dto.product.ProductUpdateRequest;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
        public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductCreationRequest product) {
        ProductResponse response = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all products", products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductUpdateRequest request) {
        ProductResponse updated = productService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Product updated successfully", updated));
    }
    @PutMapping("/{id}/rating")
    public ApiResponse<ProductResponse> updateRating(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRatingUpdateRequest request
    ) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProductRating(id, request.rating()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable UUID id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched product successfully", product));
    }

}
