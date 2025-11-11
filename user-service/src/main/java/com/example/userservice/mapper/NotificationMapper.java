package com.example.userservice.mapper;

import com.example.userservice.dto.notification.NotificationCreationRequest;
import com.example.userservice.dto.notification.NotificationResponse;
import com.example.userservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isRead", constant = "false")
    Notification toModel(NotificationCreationRequest request);

    NotificationResponse toResponse(Notification notification);
}
