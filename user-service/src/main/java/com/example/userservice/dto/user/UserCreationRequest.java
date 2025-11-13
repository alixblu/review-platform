package com.example.userservice.dto.user;

//import com.example.userservice.model.User.Gender;
import com.example.userservice.model.User;
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

//    @NotNull(message = "Age is required")
//    @Min(value = 16, message = "Age must be at least 16")
//    private Integer age;
//
//    @NotNull(message = "Gender is required")
//    private User.Gender gender;

    @Nullable
    @Size(max = 255, message = "Profile picture URL must not exceed 255 characters")
    private String profilePic;
}
