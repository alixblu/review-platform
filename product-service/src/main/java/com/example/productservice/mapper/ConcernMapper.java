package com.example.productservice.mapper;

import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.model.Concern;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ConcernMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "avoid", source = "avoid")
    @Mapping(target = "recommend", source = "recommend")
    ConcernResponse toReadDTO(Concern entity);
}

