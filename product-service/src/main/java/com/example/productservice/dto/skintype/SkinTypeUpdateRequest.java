package com.example.productservice.dto.skintype;

import com.example.commonlib.enums.SkinTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkinTypeUpdateRequest {

    @NotNull(message = "Type cannot be null")
    private SkinTypeEnum type;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 300, message = "Avoid field must be less than 300 characters")
    private String avoid;

    @Size(max = 300, message = "Recommend field must be less than 300 characters")
    private String recommend;
}