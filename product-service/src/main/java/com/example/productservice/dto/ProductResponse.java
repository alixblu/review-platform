package com.example.productservice.dto;

import com.example.productservice.model.CategoryEnum;
import com.example.productservice.model.Status;

import java.util.List;
import java.util.UUID;

public record ProductResponse(UUID id, String name, UUID brand, CategoryEnum categoryEnum,
                              String ingredients, List<String> skinTypeEnum, List<String> concernTypeEnum,
                              String description, String imageUrl, Long price, Float rating, Status status) {

}
