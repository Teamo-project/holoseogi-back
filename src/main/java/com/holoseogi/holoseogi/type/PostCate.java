package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum PostCate {

    INFO("정보"),
    FREE("자유");

    private String label;

    private static final Map<String, PostCate> BY_LABEL = new HashMap<>();

    public static PostCate findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null);
    }

}
