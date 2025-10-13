package com.example.productservice.model;

import lombok.Getter;

@Getter

public enum SkinTypeEnum {
    OILY("oily"),
    DRY("dry"),
    COMBINATION("combination"),
    SENSITIVE("sensitive");

    private final String value;
    SkinTypeEnum(String value) {
        this.value = value;
    }

    //validate enum and cast to name
    public static String validateEnum(String value) {
        for (SkinTypeEnum type : values()) {
            if (type.value.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type.name();
            }
        }
        throw new IllegalArgumentException("Unknown concern type: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}