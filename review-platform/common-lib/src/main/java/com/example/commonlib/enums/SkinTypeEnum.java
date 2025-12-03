package com.example.commonlib.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SkinTypeEnum fromValue(String value) {
        for (SkinTypeEnum type : SkinTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(value) || type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SkinType value: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}