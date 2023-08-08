package com.holoseogi.holoseogi.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "APPLY_MENTORING_TABLE")
public class ApplyMentee extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentoring_id")
    private Mentoring mentoring;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private User applicant;

    @Lob
    private String description;

    @Builder
    public ApplyMentee(Mentoring mentoring, User applicant, String description) {
        this.mentoring = mentoring;
        this.applicant = applicant;
        this.description = description;
    }
}
