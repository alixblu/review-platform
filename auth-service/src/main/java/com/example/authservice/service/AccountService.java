package com.example.authservice.service;


import com.example.authservice.dto.AccountCreationRequest;
import com.example.authservice.dto.AccountResponse;
import com.example.authservice.mapper.AccountMapper;
import com.example.authservice.model.Account;
import com.example.authservice.repository.AccountRepository;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    final  private AccountRepository accountRepository;
    final private AccountMapper accountMapper;

    public AccountResponse createAccount(AccountCreationRequest accountCreationRequest) {
        if (accountRepository.findByEmail(accountCreationRequest.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EXISTED, "This email has been existed");
        }

        Account account = accountMapper.toAccount(accountCreationRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        account.setPassword(passwordEncoder.encode(accountCreationRequest.getPassword()));

        accountRepository.save(account);
        log.info("Account created successfully");
        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElse(null);
        if (account == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Account not found");
        }
        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse getAccountById(UUID id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return accountMapper.toAccountResponse(account.get());
        }
        throw new AppException(ErrorCode.NOT_FOUND, "Account not found");
    }
}
