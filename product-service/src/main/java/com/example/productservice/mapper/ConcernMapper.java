package com.example.productservice.mapper;

import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.dto.concern.ConcernUpdateRequest;
import com.example.productservice.model.Concern;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ConcernMapper {

    // ENTITY -> DTO (response)
    ConcernResponse toResponse(Concern concern);

    // UPDATE: DTO -> ENTITY (in-place update)
    void updateEntityFromDTO(ConcernUpdateRequest dto, @MappingTarget Concern entity);
}
