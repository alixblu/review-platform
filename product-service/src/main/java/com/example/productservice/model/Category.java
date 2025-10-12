package com.example.productservice.model;

import lombok.Getter;

@Getter
public enum Category {
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

    Category(String value) {
        this.value = value;
    }

    public static String fromString(String input) {
        for (Category c : values()) {
            if (c.name().equalsIgnoreCase(input) || c.value.equalsIgnoreCase(input)) {
                return c.name();
            }
        }
        throw new IllegalArgumentException("Invalid category: " + input);
    }
}

