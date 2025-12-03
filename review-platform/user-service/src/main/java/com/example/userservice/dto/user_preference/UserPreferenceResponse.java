package com.example.userservice.dto.user_preference;

import lombok.*;

import java.time.Instant;
import java.util.List;

import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceResponse {

    private String id;
    private String userId;
    private SkinTypeEnum skinType;
    private List<ConcernTypeEnum> concerns;
    private Instant updatedAt;
}
