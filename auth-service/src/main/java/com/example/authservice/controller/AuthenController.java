package com.example.authservice.controller;
import com.example.authservice.dto.AccountResponse;
import com.example.authservice.dto.AuthenResponse;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.service.AccountService;
import com.example.authservice.service.AuthenService;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenService authenService;

    @PostMapping("/login")
    ResponseEntity<ApiResponse<Boolean>> authenthicate (@RequestBody @Valid LoginRequest loginRequest){
        boolean result = authenService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Login successfully", result));
    }

}
