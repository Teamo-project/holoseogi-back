package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class MentoringDetailResp {
    private Long id;
    private String title;
    private String description;
    private Integer limited;
    private String category;
    private Integer count;
    private Boolean isReceipt;
    private MentorInfo mentorInfo;

    public MentoringDetailResp(Mentoring mentoring) {
        this.id = mentoring.getId();
        this.title = mentoring.getTitle();
        this.description = mentoring.getDescription();
        this.limited = mentoring.getLimited();
        this.category = mentoring.getCategory().getLabel();
        this.count = (Objects.isNull(mentoring.getCount()))? 0 : mentoring.getCount();
        this.isReceipt = (Objects.isNull(mentoring.getIsReceipt()))? true : mentoring.getIsReceipt();
        this.mentorInfo = new MentorInfo(mentoring.getMentor());
    }

    @ToString
    @Getter
    public class MentorInfo {
        private Long id;
        private String email;
        private String name;

        public MentorInfo(User mentor) {
            this.id = mentor.getId();
            this.email = mentor.getEmail();
            this.name = mentor.getName();
        }
    }
}
