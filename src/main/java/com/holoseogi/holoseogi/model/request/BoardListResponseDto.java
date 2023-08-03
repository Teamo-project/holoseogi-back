package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.Board;
import lombok.Getter;

@Getter
public class BoardListResponseDto {

    private String title;
    private String content;
    private String category;

    public BoardListResponseDto(Board entity) {

        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
    }
}
