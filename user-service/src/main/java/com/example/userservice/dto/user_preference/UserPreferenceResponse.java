package com.example.userservice.dto.user_preference;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceResponse {

    private String id;
    private UUID userId;
    private String skinType;
    private List<String> concerns;
    private String preferencesText;
    private String embedding;
    private Instant updatedAt;
}
