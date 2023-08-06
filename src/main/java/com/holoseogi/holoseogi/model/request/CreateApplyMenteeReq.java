package com.holoseogi.holoseogi.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import lombok.*;

@ToString
@Getter
public class CreateApplyMenteeReq {
    private final String description;

    @JsonCreator
    public CreateApplyMenteeReq(String description) {
        this.description = description;
    }

    public ApplyMentee toEntity(User loginUser, Mentoring mentoring) {
        return ApplyMentee.builder()
                .applicant(loginUser)
                .mentoring(mentoring)
                .description(description)
                .build();
    }
}
