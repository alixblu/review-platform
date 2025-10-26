package com.example.productservice.mapper;

import com.example.productservice.dto.product.ProductCreationRequest;
import com.example.productservice.dto.product.ProductResponse;
import com.example.productservice.model.Product;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductMapper {
    Product toModel(ProductCreationRequest request);
    ProductResponse toResponse(Product product);
}
