package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyDetailResp {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private Long postId;
    private Long creatorId;

    public ReplyDetailResp(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.createDate = reply.getCreateDate();
        this.postId = reply.getPost().getId();
        this.creatorId = reply.getCreator().getId();
    }
}
