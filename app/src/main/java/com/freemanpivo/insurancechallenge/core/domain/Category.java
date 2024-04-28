package com.freemanpivo.insurancechallenge.core.domain;

import java.util.Locale;

public enum Category {
    VIDA, AUTO, VIAGEM, RESIDENCIAL, PATRIMONIAL;

    public static boolean contains(String category) {
        for (Category type: Category.values()) {
            if (type.name().equalsIgnoreCase(category)) return true;
        }

        return false;
    }

    public static Category from(String category) {
        for (Category type: Category.values()) {
            if (type.name().equalsIgnoreCase(category)) {
                return type;
            }
        }

        return null;
    }
}
