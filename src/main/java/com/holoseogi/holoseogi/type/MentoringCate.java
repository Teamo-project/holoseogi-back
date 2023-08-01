package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum MentoringCate {

    LAW("법률"),
    EMPLOY("취업"),
    COUNCEL("상담"),
    OTHER("기타");
    private String label;

    private static final Map<String, MentoringCate> BY_LABEL = new HashMap<>();

    static {
        for(MentoringCate cate : values())
            BY_LABEL.put(cate.label, cate);
    }

    public static MentoringCate findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null); // valueOf시 IllegalException 뜨는게 싫어서 따로 구현
    }
}
