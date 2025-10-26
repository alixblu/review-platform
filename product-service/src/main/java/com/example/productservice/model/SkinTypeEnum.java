package com.example.productservice.model;

import com.example.productservice.exception.AppException;
import com.example.productservice.exception.ErrorCode;
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

    public static SkinTypeEnum check(String value) {
        try {
            return SkinTypeEnum.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid SkinTypeEnum value");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}