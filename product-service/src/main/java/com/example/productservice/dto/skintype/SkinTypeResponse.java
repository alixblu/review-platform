package com.example.productservice.dto.skintype;

import com.example.commonlib.enums.SkinTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkinTypeResponse {
    private UUID id;
    private SkinTypeEnum type;
    private String description;
    private String avoid;
    private String recommend;
}