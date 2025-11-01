package com.example.authservice.dto;

import com.example.authservice.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private String email;
    private String username;
    private Account.AccountRole role;
    private Account.Status status;
    private Account.Gender gender;
}
