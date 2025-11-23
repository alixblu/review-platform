package com.example.userservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.userservice.dto.user.UserCreationRequest;
import com.example.userservice.dto.user.UserResponse;
import com.example.userservice.dto.user.UserUpdateRequest;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/internal/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalUserController {

    final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreationRequest user) {
        UserResponse response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User profile created successfully", response));
    }

}
