package com.example.commonlib.enums;
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

    @Override
    public String toString() {
        return value;
    }
}