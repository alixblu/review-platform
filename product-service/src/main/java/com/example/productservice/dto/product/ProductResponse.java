package com.example.productservice.dto.product;

import com.example.productservice.model.CategoryEnum;
import com.example.productservice.model.ConcernTypeEnum;
import com.example.productservice.model.SkinTypeEnum;
import com.example.productservice.model.Status;

import java.util.List;
import java.util.UUID;

public record ProductResponse(UUID id, String name, UUID brand, CategoryEnum categoryEnum,
                              String ingredients, List<SkinTypeEnum> skinTypeEnum, List<ConcernTypeEnum> concernTypeEnum,
                              String description, String imageUrl, Long price, Float rating, Status status) {

}
