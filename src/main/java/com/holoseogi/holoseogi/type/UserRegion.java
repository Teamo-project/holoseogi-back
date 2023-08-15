package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRegion {

    SEOUL_GYUNGI("서울경기"),
    INCHEON("인천"),
    GANGWON_DO("강원도"),
    CHUNGCHEONGBUK_DO("충청북도"),
    CHUNGCHEONGNAM_DO("충청남도"),
    GYEONGSANGBUK_DO("경상북도"),
    GYEONGSANGNAM_DO("경상남도"),
    JEOLLABUK_DO("전라북도"),
    JEOLLANAM_DO("전라남도"),
    JEJU_DO("제주도");



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
