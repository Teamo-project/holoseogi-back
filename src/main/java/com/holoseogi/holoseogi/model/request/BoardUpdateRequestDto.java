package com.holoseogi.holoseogi.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {

    private String title;
    private String content;
    private String category;

    @Builder
    public BoardUpdateRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

}
