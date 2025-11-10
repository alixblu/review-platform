package com.example.adminservice.dto.response;

import com.example.adminservice.model.enums.ReportContentType;
import com.example.adminservice.model.enums.ReportType;
import com.example.adminservice.model.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private UUID id;
    private UUID contentId;
    private ReportContentType contentType;
    private UUID userReport;
    private ReportType type;
    private RequestStatus status;
    private LocalDateTime createAt;
}