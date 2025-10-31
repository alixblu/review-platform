package com.example.userservice.dto.user;

import com.example.userservice.model.User.Gender;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private UUID accId;
    private String name;
    private Integer age;
    private Gender gender;
    private String profilePic;
}
