package com.example.adminservice.dto.request;

// Import enum Status từ common-lib
import com.example.commonlib.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalEntityStatusUpdateRequest {

    @NotNull
    private Status status;
}