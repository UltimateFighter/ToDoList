package com.nahberger.todolist.domain.model;

public enum SortOrder {
    ASC,
    DESC;

    public static SortOrder fromString(final String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            return ASC;
        }
        return SortOrder.valueOf(value.trim().toUpperCase());
    }
}