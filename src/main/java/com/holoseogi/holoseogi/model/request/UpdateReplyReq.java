package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.entity.Reply;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UpdateReplyReq {

    private final String content;
    private final Post post;

    public UpdateReplyReq(String content, Post post) {

        this.content = content;
        this.post = post;
    }
//수정
public Reply toEntity(User loginUser, Post post) {
    return Reply.builder()
            .content(content)
            .creator(loginUser)
            .post(post)
            .build();
}
}