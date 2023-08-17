package com.holoseogi.holoseogi.model.entity;

import com.holoseogi.holoseogi.model.request.UpdatePostReq;
import com.holoseogi.holoseogi.type.PostCate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "POST_TABLE")
public class Post extends BaseEntity{

    @Column(name = "post_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private PostCate category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Builder
    public Post(String title, String content, PostCate category, User creator) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.creator = creator;
    }

    public void update(UpdatePostReq requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = PostCate.findByLabel(requestDto.getCategory());
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
