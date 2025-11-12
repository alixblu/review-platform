package com.example.adminservice.dto.response;

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
public class ProductRequestResponse {
    private UUID id;
    private UUID userId;
    private String officialLink;
    private LocalDateTime createAt;
    private RequestStatus status;
}