package com.example.productservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.dto.product.ProductCreationRequest;
import com.example.productservice.dto.product.ProductResponse;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.productservice.dto.product.ProductUpdateRequest;
import java.util.UUID;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final KnowledgeBaseS3Service knowledgeBaseS3Service;

    public ProductResponse createProduct(ProductCreationRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new AppException(ErrorCode.EXISTED, "Product already exists");
        }

        // Use mapper to convert request → model
        Product product = productMapper.toModel(request);

        productRepository.save(product);
        log.info("Create product successfully");

        // Upload to knowledge base S3 (non-blocking)
        try {
            knowledgeBaseS3Service.uploadProductJson(product);
        } catch (Exception e) {
            log.error("Failed to upload product to knowledge base", e);
        }

        // Map model → response
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse updateProduct(UUID id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Product not found"));

        // Dùng mapper để update các field không null
        productMapper.updateProductFromRequest(request, product);

        productRepository.save(product);
        log.info("Updated product successfully: {}", product.getName());

        // Upload to knowledge base S3 (non-blocking)
        try {
            knowledgeBaseS3Service.uploadProductJson(product);
        } catch (Exception e) {
            log.error("Failed to upload product to knowledge base", e);
        }

        return productMapper.toResponse(product);
    }

    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Product not found"));

        return productMapper.toResponse(product);
    }
}
