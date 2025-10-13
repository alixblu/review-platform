package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.CategoryEnum;
import com.example.productservice.model.ConcernTypeEnum;
import com.example.productservice.model.Product;
import com.example.productservice.model.SkinTypeEnum;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
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
            category = CategoryEnum.validateEnum(request.categoryEnum());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid category: " + request.categoryEnum()
            );
        }
        List<String> skinTypeEnumList;
        try {
            skinTypeEnumList = request.skinTypeEnum().stream()
                    .map(SkinTypeEnum::validateEnum)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid skin type: " + e.getMessage()
            );
        }
        List<String> concernTypeEnumList;
        try {
            concernTypeEnumList = request.concernTypeEnum().stream()
                    .map(ConcernTypeEnum::validateEnum)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid skin type: " + e.getMessage()
            );
        }

        Product product = Product.builder()
                .name(request.name())
                .categoryEnum(CategoryEnum.valueOf(category))
                .ingredients(request.ingredients())
                .skinTypesEnum(request.skinTypeEnum())
                .concernTypesEnum(request.concernTypeEnum())
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
                product.getCategoryEnum(),
                product.getIngredients(),
                product.getSkinTypesEnum(),
                product.getConcernTypesEnum(),
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
                        product.getCategoryEnum(),
                        product.getIngredients(),
                        product.getSkinTypesEnum(),
                        product.getConcernTypesEnum(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getRating(),
                        product.getStatus())
                ).toList();
    }
}
