package com.holoseogi.holoseogi.model.request;


import com.holoseogi.holoseogi.model.entity.Board;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//카테고리 4개 설정해야돼
@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {
    private String title;
    private String content;
    private String category;





    @Builder
    public BoardCreateRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Board toEntity(User loginUser) {
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .user(loginUser)
                .build();


    }
}
