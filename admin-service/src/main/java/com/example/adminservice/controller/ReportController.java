package com.example.adminservice.controller;

import com.example.adminservice.dto.request.ReportCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ReportResponse;
import com.example.adminservice.model.enums.RequestStatus;
import com.example.adminservice.service.ReportService;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<ReportResponse> createReport(
            @Valid @RequestBody ReportCreationRequest request) {
        log.info("Request to create report for contentId: {}",
                request.getContentId());
        ReportResponse response = reportService.createReport(request);
        return new ApiResponse<>("Report created successfully", response);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ReportResponse>> getAllReports(
            @RequestParam(required = false) RequestStatus status) {
        log.info("Request to get all reports with status: {}", status);
        List<ReportResponse> responses = reportService.getAllReports(status);
        return new ApiResponse<>("Reports retrieved successfully", responses);
    }


    @GetMapping("/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ReportResponse> getReportById(
            @PathVariable UUID reportId) {
        log.info("Request to get report by id: {}", reportId);
        ReportResponse response = reportService.getReport(reportId);
        return new ApiResponse<>("Report retrieved successfully", response);
    }


    @PutMapping("/{reportId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ReportResponse> updateReportStatus(
            @PathVariable UUID reportId,
            @Valid @RequestBody RequestStatusUpdateRequest request) {
        log.info("Request to update status for reportId: {}", reportId);
        ReportResponse response =
                reportService.updateReportStatus(reportId, request);
        return new ApiResponse<>("Report status updated", response);
    }
}