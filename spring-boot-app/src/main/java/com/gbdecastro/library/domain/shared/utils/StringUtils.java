package com.gbdecastro.library.domain.shared.utils;

import java.util.Objects;

public abstract class StringUtils {

    public static final String COMMA = ",";

    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }

    public static boolean isGreaterThan(String value, Integer limit) {
        return !StringUtils.isEmpty(value) || value.length() > limit;
    }
}
