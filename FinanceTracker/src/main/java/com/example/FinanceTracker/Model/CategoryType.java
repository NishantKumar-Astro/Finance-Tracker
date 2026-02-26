package com.example.FinanceTracker.Model;

public enum CategoryType {
    INCOME(1),
    EXPENSE(2);

    private final int code;

    CategoryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CategoryType fromCode(int code) {
        for (CategoryType type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}