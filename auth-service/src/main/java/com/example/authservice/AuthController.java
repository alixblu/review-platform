package com.example.authservice;

import com.example.commonlib.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<?> exchangeAuthCodeForTokens(@RequestBody Map<String, String> payload) {
        return authService.exchangeAuthCodeForTokens(payload);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(@RequestHeader(value = "Authorization", required = false) String authorization,
                                             org.springframework.security.core.Authentication authentication) {
        return authService.me(authorization, authentication);
    }
}
