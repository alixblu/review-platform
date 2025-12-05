package com.example.userservice.dto.user_preference;

import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceCreationRequest {

    @NotNull(message = "User ID không được để trống")
    private String userId;

    private SkinTypeEnum skinType;

    private List<ConcernTypeEnum> concerns;
}
