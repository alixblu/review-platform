package com.example.commonlib.enums;

import lombok.Getter;

@Getter
public enum NotiType {
    FOLLOW("follow"),
    LIKE("like"),
    COMMENT("comment"),
    SYSTEM("system"),
    REMINDER("reminder");

    private final String value;

    NotiType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
