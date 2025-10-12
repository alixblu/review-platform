package com.example.productservice.dto;

import com.example.productservice.model.Category;
import com.example.productservice.model.Status;

import java.util.List;
import java.util.UUID;

public record ProductResponse(UUID id, String name, UUID brand, Category category
                            , String ingredients, List<String> skinType, List<String> target, String description, String imageUrl, Long price, Float rating, Status status) {

}
