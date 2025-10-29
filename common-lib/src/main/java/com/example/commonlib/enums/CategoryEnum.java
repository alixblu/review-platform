package com.example.commonlib.enums;
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

