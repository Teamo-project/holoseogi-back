package com.holoseogi.holoseogi.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SearchMentoring {
    private String title;
    private String category;

    @Builder
    public SearchMentoring(String title, String category) {
        this.title = (Objects.isNull(title))? "": title;
        this.category = category;
    }
}
