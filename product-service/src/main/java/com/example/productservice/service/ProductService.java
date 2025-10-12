package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest request)
    {
        String category;
        try {
            category = Category.fromString(request.category());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid category: " + request.category()
            );
        }
        Product product = Product.builder()
                .name(request.name())
                .category(Category.valueOf(category))
                .ingredients(request.ingredients())
                .skinType(request.skinType())
                .target(request.target())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .price(request.price())
                .build();
        productRepository.save(product);
        log.info("Create product successfully");
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getIngredients(),
                product.getSkinType(),
                product.getTarget(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getRating(),
                product.getStatus());
    }

    public List<ProductResponse> getAllProducts()
    {
        return productRepository.findAll()
                .stream().map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getBrand(),
                        product.getCategory(),
                        product.getIngredients(),
                        product.getSkinType(),
                        product.getTarget(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getRating(),
                        product.getStatus())
                ).toList();
    }
}
