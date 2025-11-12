package com.example.adminservice.dto.request;

import com.example.adminservice.model.enums.ReportContentType;
import com.example.adminservice.model.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreationRequest {

    @NotNull
    private UUID contentId;

    @NotNull
    private ReportContentType contentType;

    @NotNull
    private ReportType type;

}