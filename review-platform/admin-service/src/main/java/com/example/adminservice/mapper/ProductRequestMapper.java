package com.example.adminservice.mapper;

import com.example.adminservice.dto.request.ProductRequestCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ProductRequestResponse;
import com.example.adminservice.model.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    ProductRequest toProductRequest(ProductRequestCreationRequest request);

    ProductRequestResponse toProductRequestResponse(
            ProductRequest productRequest);


    void updateProductRequestFromDto(RequestStatusUpdateRequest dto,
                                     @MappingTarget
                                     ProductRequest productRequest);
}