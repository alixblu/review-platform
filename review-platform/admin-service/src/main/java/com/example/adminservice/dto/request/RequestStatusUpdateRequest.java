package com.example.adminservice.dto.request;

import com.example.adminservice.model.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatusUpdateRequest {

    @NotNull
    private RequestStatus status;
}