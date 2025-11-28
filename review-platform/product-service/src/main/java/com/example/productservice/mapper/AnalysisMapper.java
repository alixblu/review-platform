package com.example.productservice.mapper;

import com.example.productservice.dto.analysis.AnalysisCreationRequest;
import com.example.productservice.dto.analysis.AnalysisResponse;
import com.example.productservice.model.Analysis;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AnalysisMapper {
    // Map từ DTO request → Entity
    @Mapping(target = "id", ignore = true) // ID thường do DB tự sinh
    Analysis toModel(AnalysisCreationRequest request);

    // Map từ Entity → DTO response
    AnalysisResponse toResponse(Analysis analysis);
}
