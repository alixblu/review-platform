package com.example.productservice.model;

import com.example.productservice.exception.AppException;
import com.example.productservice.exception.ErrorCode;
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


}

