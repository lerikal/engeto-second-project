package com.engeto.engeto_second_project.model;

public enum SortType {
    PRICE,
    NAME,
    QUANTITY;

    public static SortType from(String value) {
        if (value == null) return null;

        try {
            return SortType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}