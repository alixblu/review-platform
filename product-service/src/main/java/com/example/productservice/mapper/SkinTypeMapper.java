package com.example.productservice.mapper;

import com.example.productservice.dto.skintype.SkinTypeResponse;
import com.example.productservice.dto.skintype.SkinTypeUpdateRequest;
import com.example.productservice.model.SkinType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SkinTypeMapper {

    SkinTypeResponse toReadDTO(SkinType entity);

    void updateEntityFromDTO(SkinTypeUpdateRequest dto, @MappingTarget SkinType entity);
}
