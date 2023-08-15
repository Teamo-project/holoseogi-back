package com.holoseogi.holoseogi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentoringDetailResp {
    private Long id;
    private String title;
    private String description;
    private Integer limited;
    private String category;
    private Integer count;
    private Boolean isReceipt;
    private MentorInfo mentorInfo;
    private LocalDateTime createDate;
    private List<MenteeInfo> menteees;

    public MentoringDetailResp(Mentoring mentoring, Boolean isOwn) {
        this.id = mentoring.getId();
        this.title = mentoring.getTitle();
        this.description = mentoring.getDescription();
        this.limited = mentoring.getLimited();
        this.category = mentoring.getCategory().getLabel();
        this.count = (Objects.isNull(mentoring.getCount())) ? 0 : mentoring.getCount();
        this.isReceipt = (Objects.isNull(mentoring.getIsReceipt())) ? true : mentoring.getIsReceipt();
        this.mentorInfo = new MentorInfo(mentoring.getMentor());
        this.createDate = mentoring.getCreateDate();
        this.menteees = isOwn? this.setMentees(mentoring) : null;
    }

    // 본인의 글일 때 메소드 호출하는 로직 필요
    public List<MenteeInfo> setMentees(Mentoring mentoring) {
        if (mentoring.getMentees().size() > 0) {
            return mentoring.getMentees().stream()
                    .map(MenteeInfo::new)
                    .collect(Collectors.toList());
        }
        else return null;
    }
    
    @ToString
    @Getter
    public class MentorInfo {
        private Long id;
        private String email;
        private String name;

        private MentorInfo(User mentor) {
            this.id = mentor.getId();
            this.email = mentor.getEmail();
            this.name = mentor.getName();
        }
    }

    @ToString
    @Getter
    public class MenteeInfo {
        private Long applyMenteeId;
        private String menteeImg;
        private String menteeEmail;
        private String menteeName;

        private MenteeInfo(ApplyMentee mentee) {
            this.applyMenteeId = mentee.getId();
            this.menteeImg = (Objects.isNull(mentee.getApplicant().getImg())) ? "null" : mentee.getApplicant().getImg();
            this.menteeEmail = mentee.getApplicant().getEmail();
            this.menteeName = mentee.getApplicant().getName();
        }
    }
}
