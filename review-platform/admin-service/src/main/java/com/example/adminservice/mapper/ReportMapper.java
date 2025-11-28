package com.example.adminservice.mapper;

import com.example.adminservice.dto.request.ReportCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ReportResponse;
import com.example.adminservice.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userReport", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    Report toReport(ReportCreationRequest request);

    ReportResponse toReportResponse(Report report);

    void updateReportFromDto(RequestStatusUpdateRequest dto,
                             @MappingTarget Report report);
}