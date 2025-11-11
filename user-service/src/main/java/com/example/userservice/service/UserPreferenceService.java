package com.example.userservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.userservice.dto.user_preference.*;
import com.example.userservice.mapper.UserPreferenceMapper;
import com.example.userservice.model.User;
import com.example.userservice.model.UserPreference;
import com.example.userservice.repository.UserPreferenceRepository;
import com.example.userservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPreferenceService {

    UserPreferenceRepository userPreferenceRepository;
    UserRepository userRepository;
    UserPreferenceMapper userPreferenceMapper;

    public UserPreferenceResponse createUserPreference(UserPreferenceCreationRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));

        if (userPreferenceRepository.findByUser_UserId(user.getUserId()).isPresent()) {
            throw new AppException(ErrorCode.EXISTED, "User preference already exists");
        }

        UserPreference preference = userPreferenceMapper.toModel(request);
        preference.setUser(user);
        preference.setUpdatedAt(Instant.now());

        UserPreference saved = userPreferenceRepository.save(preference);
        return userPreferenceMapper.toResponse(saved);
    }

    public UserPreferenceResponse updateUserPreference(UUID userId, UserPreferenceUpdateRequest request) {
        UserPreference preference = userPreferenceRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User preference not found"));

        userPreferenceMapper.updateFromRequest(request, preference);
        preference.setUpdatedAt(Instant.now());

        userPreferenceRepository.save(preference);
        return userPreferenceMapper.toResponse(preference);
    }

    public UserPreferenceResponse getUserPreference(UUID userId) {
        UserPreference preference = userPreferenceRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User preference not found"));
        return userPreferenceMapper.toResponse(preference);
    }

    public void deleteUserPreference(UUID userId) {
        UserPreference preference = userPreferenceRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User preference not found"));
        userPreferenceRepository.delete(preference);
    }
}
