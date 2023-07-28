package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MentoringListResp {
    private Long id;
    private String title;
    private String category;
    private Integer count;
    private Integer limited;
    private Boolean isReceipt;

    public MentoringListResp(Mentoring mentoring) {
        this.id = mentoring.getId();
        this.title = mentoring.getTitle();
        this.category = mentoring.getCategory().getLabel();
        this.count = mentoring.getCount();
        this.limited = mentoring.getLimited();
        this.isReceipt = mentoring.getIsReceipt();
    }
}
