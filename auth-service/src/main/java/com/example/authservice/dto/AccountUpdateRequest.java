package com.example.authservice.dto;

import com.example.authservice.model.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequest {

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password; // plain text, re-hash before saving

    private Account.Status status;

    private Account.Gender gender;
}
