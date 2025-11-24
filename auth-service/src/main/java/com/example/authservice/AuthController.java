package com.example.authservice;

import com.example.commonlib.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<ApiResponse<?>> exchangeAuthCodeForTokens(@RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(new ApiResponse<>("Get token successfully", authService.exchangeAuthCodeForTokens(payload)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(new ApiResponse<>("Token refreshed successfully", authService.refreshToken(payload)));
    }

}
