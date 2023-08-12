package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRegion {

    SEOUL_GYUNGI("서울경기");

    private String label;

    private static final Map<String, UserRegion> BY_LABEL = new HashMap<>();

    static {
        for (UserRegion region : values()) {
            BY_LABEL.put(region.label, region);
        }
    }

    public static UserRegion findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null);
    }
}
