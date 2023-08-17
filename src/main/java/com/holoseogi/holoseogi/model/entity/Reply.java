package com.holoseogi.holoseogi.model.entity;

import com.holoseogi.holoseogi.model.request.UpdateReplyReq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REPLY_TABLE")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;
    @Builder
    public Reply(String content, Post post, User creator) {
        this.content = content;
        this.post = post;
        this.creator = creator;
    }

    public void update(UpdateReplyReq requestDto) {
        this.content = requestDto.getContent();
    }


}