package com.example.productservice.dto;

import java.util.List;

public record ProductRequest(String name, String categoryEnum, String ingredients,
                             List<String> skinTypeEnum, List<String> concernTypeEnum, String description, String imageUrl, Long price) {

}
