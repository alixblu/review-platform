package com.example.userservice.dto.user_preference;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceUpdateRequest {

    @Size(max = 50, message = "Skin type tối đa 50 ký tự")
    private String skinType;

    private List<String> concerns;

    @Size(max = 1000, message = "Preferences text tối đa 1000 ký tự")
    private String preferencesText;

}
