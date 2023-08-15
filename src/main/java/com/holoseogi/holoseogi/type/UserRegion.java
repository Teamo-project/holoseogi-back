package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRegion {

    SEOUL("서울"),
    INCHEN("인천"),
    GYUNGI("경기"),
    CHUNCHEN_NORTH("충청북도"),
    CHUNCHEN_SOUTH("충청남도"),
    GYUNSANG_NORTH("경상북도"),
    GYUNSANG_SOUTH("경상남도"),
    JEOLLA_NORTH("전라북도"),
    JEOLLA_SOUTH("전라남도"),
    GANGWON("강원도");

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
