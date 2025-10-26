package com.example.productservice.controller;

import com.example.productservice.dto.ApiResponse;import com.example.productservice.dto.product.ProductCreationRequest;
import com.example.productservice.dto.product.ProductResponse;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductCreationRequest product) {
        ProductResponse response = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully", response));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Fetched all products", products));
    }
}
