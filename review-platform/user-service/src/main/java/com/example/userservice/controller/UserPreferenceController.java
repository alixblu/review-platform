package com.example.userservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.userservice.dto.user_preference.*;
import com.example.userservice.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.String;


@RestController
@RequestMapping("/api/user/preference")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPreferenceController {

    final UserPreferenceService userPreferenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> createPreference(
            @RequestBody @Valid UserPreferenceCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User preference created successfully",
                        userPreferenceService.createUserPreference(request)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> updatePreference(
            @PathVariable String userId,
            @RequestBody @Valid UserPreferenceUpdateRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>("User preference updated successfully",
                        userPreferenceService.updateUserPreference(userId, request)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> getPreference(@PathVariable String userId) {
        return ResponseEntity.ok(
                new ApiResponse<>("User preference retrieved successfully",
                        userPreferenceService.getUserPreference(String.valueOf(userId))));
    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<ApiResponse<Void>> deletePreference(@PathVariable String userId) {
//        userPreferenceService.deleteUserPreference(userId);
//        return ResponseEntity.ok(new ApiResponse<>("User preference deleted successfully", null));
//    }
}
