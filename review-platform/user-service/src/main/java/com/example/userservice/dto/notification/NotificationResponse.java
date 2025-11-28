package com.example.userservice.dto.notification;

import com.example.userservice.model.Notification.NotiType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private String id;
    private UUID userId;
    private NotiType type;
    private String description;
    private LocalDateTime createAt;
    private boolean isRead;
    private String endpoint;
}
