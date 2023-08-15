package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class MyPageMentoringListResp {
    private Long id;
    private String title;
    private String category;
    private Integer count;
    private Integer limited;
    private Boolean isReceipt;
    private LocalDateTime createDate;
    private String creatorName;
    private String description;

    public MyPageMentoringListResp(Mentoring mentoring) {
        this.id = mentoring.getId();
        this.title = mentoring.getTitle();
        this.category = mentoring.getCategory().getLabel();
        this.count = mentoring.getCount();
        this.limited = mentoring.getLimited();
        this.isReceipt = mentoring.getIsReceipt();
        this.createDate = mentoring.getCreateDate();
        this.creatorName = mentoring.getMentor().getName();
        this.description = mentoring.getDescription();
    }

    public MyPageMentoringListResp(ApplyMentee applyMentee) {
        this.id = applyMentee.getId();
        this.title = applyMentee.getMentoring().getTitle();
        this.category = applyMentee.getMentoring().getCategory().getLabel();
        this.count = applyMentee.getMentoring().getCount();
        this.limited = applyMentee.getMentoring().getLimited();
        this.isReceipt = applyMentee.getMentoring().getIsReceipt();
        this.createDate = applyMentee.getMentoring().getCreateDate();
        this.creatorName = applyMentee.getMentoring().getMentor().getName();
        this.description = applyMentee.getDescription();
    }
}
