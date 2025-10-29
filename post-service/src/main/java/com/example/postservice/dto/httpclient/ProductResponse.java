package com.example.postservice.dto.httpclient;



import com.example.commonlib.enums.CategoryEnum;
import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import com.example.commonlib.enums.Status;

import java.util.List;
import java.util.UUID;

public record ProductResponse(UUID id, String name, UUID brand_id, CategoryEnum categoryEnum,
                              String ingredients, List<SkinTypeEnum> skinTypeEnum, List<ConcernTypeEnum> concernTypeEnum,
                              String description, String imageUrl, Long price, Float rating, Status status) {

}
