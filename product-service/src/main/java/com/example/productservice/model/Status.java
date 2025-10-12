package com.example.productservice.model;


import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("active"),
    HIDDEN("hidden");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}