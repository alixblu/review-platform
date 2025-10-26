package com.example.productservice.service;

import com.example.productservice.dto.product.ProductCreationRequest;
import com.example.productservice.dto.product.ProductResponse;
import com.example.productservice.exception.AppException;
import com.example.productservice.exception.ErrorCode;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.model.CategoryEnum;
import com.example.productservice.model.ConcernTypeEnum;
import com.example.productservice.model.Product;
import com.example.productservice.model.SkinTypeEnum;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductCreationRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new AppException(ErrorCode.EXISTED, "Product already exists");
        }

        // Use mapper to convert request → model
        Product product = productMapper.toModel(request);

        productRepository.save(product);
        log.info("Create product successfully");

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
}
