package com.example.userservice.dto.user_preference;

import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceUpdateRequest {

    private SkinTypeEnum skinType;

    private List<ConcernTypeEnum> concerns;
}
