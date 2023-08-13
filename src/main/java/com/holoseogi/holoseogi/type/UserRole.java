package com.holoseogi.holoseogi.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    BEFORE("ROLE_BEFORE", "before"),
    MENTOR("ROLE_MENTOR", "mentor"),
    MENTEE("ROLE_MENTEE", "mentee");

    private final String role;
    private final String label;

    private static final Map<String, UserRole> BY_LABEL = new HashMap<>();

    static {
        for (UserRole role : values()) {
            BY_LABEL.put(role.label, role);
        }
    }

    public static UserRole findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null);
    }
}