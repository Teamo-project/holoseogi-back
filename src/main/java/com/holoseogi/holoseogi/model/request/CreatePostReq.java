package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.PostCate;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreatePostReq {

    private final String title;

    private final String content;

    private final String category;

    public CreatePostReq(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Post toEntity(User loginUser) {
        return Post.builder()
                .creator(loginUser)
                .content(content)
                .title(title)
                .category(PostCate.findByLabel(category))
                .build();
    }
}
