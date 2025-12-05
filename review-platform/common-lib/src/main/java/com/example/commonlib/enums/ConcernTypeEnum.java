package com.example.commonlib.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ConcernTypeEnum fromValue(String value) {
        for (ConcernTypeEnum type : ConcernTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(value) || type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ConcernType value: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}

