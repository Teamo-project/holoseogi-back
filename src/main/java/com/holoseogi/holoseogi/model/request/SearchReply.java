package com.holoseogi.holoseogi.model.request;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SearchReply {
    private String content;


    @Builder
    public SearchReply(String content) {
        this.content = (Objects.isNull(content))? "": content;
    }
}
