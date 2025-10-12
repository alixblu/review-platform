package com.example.productservice.dto;

import com.example.productservice.model.Category;

import java.util.List;

public record ProductRequest(String name, String category, String ingredients,
                             List<String> skinType, List<String> target, String description, String imageUrl, Long price) {

}
