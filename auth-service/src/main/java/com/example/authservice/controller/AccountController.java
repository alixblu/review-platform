package com.example.authservice.controller;

import com.example.authservice.dto.AccountCreationRequest;
import com.example.authservice.dto.AccountResponse;
import com.example.authservice.model.Account;
import com.example.authservice.service.AccountService;
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
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody @Valid  AccountCreationRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Account Created", accountResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable UUID id) {
        AccountResponse accountResponse = accountService.getAccountById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Account Found", accountResponse));
    }


}
