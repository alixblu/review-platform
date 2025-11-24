package com.example.authservice.httpclient;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {

    @NotNull(message = "Account ID is required")
    private UUID accId;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Nullable
    @Size(max = 255, message = "Profile picture URL must not exceed 255 characters")
    private String profilePic;
}
