package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ApplyMenteeInfoResp {
    private Long id;
    private String description;
    private Long mentoringId;
    private String mentoringTitle;
    private Long applicantId;
    private String applicantEmail;
    private String applicantName;
    private String applicantGender;
    private Integer applicantAge;
    private String applicantRegion;

    public ApplyMenteeInfoResp(ApplyMentee applyMentee) {
        this.id = applyMentee.getId();
        this.description = applyMentee.getDescription();
        this.mentoringId = applyMentee.getMentoring().getId();
        this.mentoringTitle = applyMentee.getMentoring().getTitle();
        this.applicantId = applyMentee.getApplicant().getId();
        this.applicantEmail = applyMentee.getApplicant().getEmail();
        this.applicantName = applyMentee.getApplicant().getName();
        this.applicantGender = applyMentee.getApplicant().getGender().getLabel();
        this.applicantAge = applyMentee.getApplicant().getAge();
        this.applicantRegion = applyMentee.getApplicant().getRegion().getLabel();
    }
}
