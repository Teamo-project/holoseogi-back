package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.MentoringCate;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateMentoringReq {
    private final String title;
    private final String description;
    private final String category;
    private final Integer limited;

    public Mentoring toEntity(User loginUser) {
        return Mentoring.builder()
                .title(title)
                .description(description)
                .limited(limited)
                .category(MentoringCate.findByLabel(category))
                .mentor(loginUser)
                .build();
    }
}

