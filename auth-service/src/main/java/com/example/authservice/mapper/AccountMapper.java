package com.example.authservice.mapper;

import com.example.authservice.dto.AccountCreationRequest;
import com.example.authservice.dto.AccountResponse;
import com.example.authservice.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toAccountResponse(Account account);

    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "ACTIVE")
    Account toAccount(AccountCreationRequest request);
}
