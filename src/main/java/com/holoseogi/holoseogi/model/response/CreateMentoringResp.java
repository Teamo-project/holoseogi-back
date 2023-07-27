package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class CreateMentoringResp {
    private Long id;
    private String title;
    private String description;
    private Integer limited;
    private Integer count;
    private Boolean isReceipt;
    private MentorInfo mentorInfo;

    public CreateMentoringResp(Mentoring mentoring) {
        this.id = mentoring.getId();
        this.title = mentoring.getTitle();
        this.description = mentoring.getDescription();
        this.limited = mentoring.getLimited();
        this.count = 0;
        this.isReceipt = true;
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
