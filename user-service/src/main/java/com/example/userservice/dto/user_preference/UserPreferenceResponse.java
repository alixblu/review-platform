package com.example.userservice.dto.user_preference;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceResponse {

    private String id;
    private String userId;
    private String skinType;
    private List<String> concerns;
    private Instant updatedAt;
}
