package com.example.productservice.mapper;

import com.example.productservice.dto.brand.BrandCreationRequest;
import com.example.productservice.dto.brand.BrandResponse;
import com.example.productservice.dto.brand.BrandUpdateRequest;
import com.example.productservice.model.Brand;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BrandMapper {

    // Chuyển từ DTO request sang entity
    Brand toModel(BrandCreationRequest request);

    // Chuyển từ entity sang DTO response
    BrandResponse toResponse(Brand brand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrandFromRequest(BrandUpdateRequest request, @MappingTarget Brand brand);
}