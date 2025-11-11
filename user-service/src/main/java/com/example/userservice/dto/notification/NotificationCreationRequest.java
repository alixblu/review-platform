package com.example.userservice.dto.notification;

import com.example.userservice.model.Notification.NotiType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreationRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Notification type is required")
    private NotiType type;

    @NotBlank(message = "Description is required")
    private String description;

    private String endpoint;
}
