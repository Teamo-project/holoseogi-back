package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private String title;
    private String content;
    private String category;

    private LocalDateTime createDate;



    public BoardResponseDto(Board entity) {


        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();

        this.createDate = entity.getCreateDate();



    }
}
