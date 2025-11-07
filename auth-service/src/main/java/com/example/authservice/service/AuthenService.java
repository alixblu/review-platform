package com.example.authservice.service;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.repository.AccountRepository;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenService {
    private final AccountRepository accountRepository;

    public boolean authenticate(LoginRequest loginRequest) {
        var account = accountRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND, "username not existed"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(loginRequest.getPassword(), account.getPassword());
    }
}
