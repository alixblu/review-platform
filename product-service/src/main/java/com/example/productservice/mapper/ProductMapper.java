package com.example.productservice.mapper;

import com.example.productservice.dto.product.ProductCreationRequest;
import com.example.productservice.dto.product.ProductResponse;
import com.example.productservice.model.Product;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(target = "skinTypeEnum", source = "skinTypeEnum")
    @Mapping(target = "concernTypeEnum", source = "concernTypeEnum")
    Product toModel(ProductCreationRequest request);

    @Mapping(target = "skinTypeEnum", source = "skinTypeEnum")
    @Mapping(target = "concernTypeEnum", source = "concernTypeEnum")
    ProductResponse toResponse(Product product);
}
