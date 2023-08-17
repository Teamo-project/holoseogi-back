package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDetailResp {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Long creatorId;
    private String category;

    public PostDetailResp(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createDate = post.getCreateDate();
      //  this.creatorId = post.getCreator().getId();

        if (post.getCreator() != null) {
            this.creatorId = post.getCreator().getId();
        } else {
            this.creatorId = -1L;  // Set creatorId to -1 if creator is null
        }
        this.category = post.getCategory().getLabel();
    }
}
