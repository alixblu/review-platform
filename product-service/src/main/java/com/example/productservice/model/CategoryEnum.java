package com.example.productservice.model;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    CLEANSER("cleanser"),
    MOISTURIZER("moisturizer"),
    TONER("toner"),
    SERUM("serum"),
    SUNSCREEN("sunscreen"),
    MASK("mask"),
    EYE_CREAM("eye cream"),
    SPOT_TREATMENT("spot treatment"),
    OIL("oil");

    private final String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    public static String validateEnum(String input) {
        for (CategoryEnum c : values()) {
            if (c.name().equalsIgnoreCase(input) || c.value.equalsIgnoreCase(input)) {
                return c.name();
            }
        }
        throw new IllegalArgumentException("Invalid category: " + input);
    }
}

