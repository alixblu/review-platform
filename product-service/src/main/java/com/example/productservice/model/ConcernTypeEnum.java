package com.example.productservice.model;

import lombok.Getter;

@Getter
public enum ConcernTypeEnum {
    ACNE("acne"),
    REDNESS("redness"),
    AGING("aging"),
    PIGMENTATION("pigmentation"),
    DULLNESS("dullness"),
    LARGE_PORES("large pores"),
    UNEVEN_TEXTURE("uneven texture"),
    DARK_SPOTS("dark spots"),
    FINE_LINES("fine lines");

    private final String value;

    ConcernTypeEnum(String value) {
        this.value = value;
    }

    public static ConcernTypeEnum fromValue(String value) {
        for (ConcernTypeEnum type : ConcernTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown concern type: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}

