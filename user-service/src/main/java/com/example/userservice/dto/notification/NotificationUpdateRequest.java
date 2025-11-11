package com.example.userservice.dto.notification;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUpdateRequest {

    @NotNull(message = "Read status is required")
    private Boolean isRead;
}
