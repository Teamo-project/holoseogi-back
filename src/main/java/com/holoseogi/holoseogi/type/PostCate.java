package com.holoseogi.holoseogi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum PostCate {

    INFO("정보"),
    FREE("자유"),
    Question("질문"),
    JOB("구인구직");

    private String label;

    private static final Map<String, PostCate> BY_LABEL = new HashMap<>();

    static {
        for(PostCate cate : values())
            BY_LABEL.put(cate.label, cate);
    }

    public static PostCate findByLabel(String label) {
        return BY_LABEL.getOrDefault(label, null);
    }

}
