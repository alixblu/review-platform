package com.example.userservice.service;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.userservice.dto.notification.NotificationCreationRequest;
import com.example.userservice.dto.notification.NotificationResponse;
import com.example.userservice.mapper.NotificationMapper;
import com.example.userservice.model.Notification;
import com.example.userservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationService {

    final NotificationRepository notificationRepository;
    final NotificationMapper notificationMapper;

    public NotificationResponse createNotification(NotificationCreationRequest request) {
        Notification notification = notificationMapper.toModel(request);
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    public List<NotificationResponse> getNotificationsByUserId(String userId) {
        List<Notification> list = notificationRepository.findByUserId(userId);
        return list.stream().map(notificationMapper::toResponse).collect(Collectors.toList());
    }

    public void markAsRead(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
