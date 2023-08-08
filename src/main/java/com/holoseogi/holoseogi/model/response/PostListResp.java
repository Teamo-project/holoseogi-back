package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Post;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class PostListResp {
    private Long id;
    private String title;
    private String category;
    private LocalDateTime createDate;

    public PostListResp(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory().getLabel();
        this.createDate = post.getCreateDate();
    }
}
