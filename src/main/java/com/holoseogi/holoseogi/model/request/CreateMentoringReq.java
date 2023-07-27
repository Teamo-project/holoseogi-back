package com.holoseogi.holoseogi.model.request;

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
}

