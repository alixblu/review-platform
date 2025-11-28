package com.example.userservice.dto.user_preference;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceCreationRequest {

    @NotNull(message = "User ID không được để trống")
    private String userId;

    @Size(max = 50, message = "Skin type tối đa 50 ký tự")
    private String skinType;

    private List<String> concerns;


}
