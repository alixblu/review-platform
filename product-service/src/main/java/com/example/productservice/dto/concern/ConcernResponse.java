package com.example.productservice.dto.concern;

import com.example.commonlib.enums.ConcernTypeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class ConcernResponse {
    private UUID id;
    private ConcernTypeEnum type;
    private String description;
    private String avoid;
    private String recommend;
}
