package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserGender {
    FEMALE("여"),
    MALE("남");

    private String label;
    private final static Map<String, UserGender> BY_LABEL = new HashMap<>();

    static {
        for (UserGender gender : values()) {
            BY_LABEL.put(gender.label, gender);
        }
    }

    public static UserGender findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null);
    }
}
