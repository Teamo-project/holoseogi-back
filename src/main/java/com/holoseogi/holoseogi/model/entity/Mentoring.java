package com.holoseogi.holoseogi.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@Entity
@Table(name = "MENTORING_TABLE")
public class Mentoring extends BaseEntity{

    @Column(name = "mentoring_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private Integer limited;

    @ColumnDefault("0")
    private Integer count;

    @ColumnDefault("true")
    @Column(name = "is_receipt", columnDefinition = "TINYINT(1)")
    private Boolean isReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @Builder
    public Mentoring(String title, String description, Integer limited, Integer count, Boolean isReceipt, User mentor) {
        this.title = title;
        this.description = description;
        this.limited = limited;
        this.count = count;
        this.isReceipt = isReceipt;
        this.mentor = mentor;
    }
}

