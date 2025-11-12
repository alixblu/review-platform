package com.example.adminservice.service;

import com.example.adminservice.dto.request.ReportCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ReportResponse;
import com.example.adminservice.mapper.ReportMapper;
import com.example.adminservice.model.Report;
import com.example.adminservice.model.enums.RequestStatus;
import com.example.adminservice.repository.ReportRepository;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;


    @Transactional
    public ReportResponse createReport(ReportCreationRequest request) {
        log.info("Creating new report for contentId: {}", request.getContentId());


        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userReportId = UUID.fromString(userIdStr);

        Report report = reportMapper.toReport(request);
        report.setUserReport(userReportId);
        report.setStatus(RequestStatus.UnderReview);

        Report savedReport = reportRepository.save(report);
        return reportMapper.toReportResponse(savedReport);
    }


    @Transactional
    public ReportResponse updateReportStatus(UUID reportId, RequestStatusUpdateRequest request) {
        log.info("Admin updating status for reportId: {}", reportId);

        Report existingReport = findReportById(reportId);
        reportMapper.updateReportFromDto(request, existingReport);

        Report updatedReport = reportRepository.save(existingReport);
        return reportMapper.toReportResponse(updatedReport);
    }


    public List<ReportResponse> getAllReports(RequestStatus status) {
        List<Report> reports;
        if (status != null) {
            log.info("Fetching all reports with status: {}", status);
            reports = reportRepository.findByStatus(status);
        } else {
            log.info("Fetching all reports");
            reports = reportRepository.findAll();
        }

        return reports.stream()
                .map(reportMapper::toReportResponse)
                .collect(Collectors.toList());
    }


    public ReportResponse getReport(UUID reportId) {
        log.info("Fetching report details for reportId: {}", reportId);
        Report report = findReportById(reportId);
        return reportMapper.toReportResponse(report);
    }

    // --- Helper method ---
    private Report findReportById(UUID reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Report not found with id: " + reportId));
    }
}