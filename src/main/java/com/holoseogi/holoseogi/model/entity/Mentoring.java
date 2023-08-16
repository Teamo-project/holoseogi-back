package com.holoseogi.holoseogi.model.entity;

import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.type.MentoringCate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private MentoringCate category;

    @ColumnDefault("0")
    private Integer count;

    @ColumnDefault("true")
    @Column(name = "is_receipt", columnDefinition = "TINYINT(1)")
    private Boolean isReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @OneToMany(mappedBy = "mentoring", cascade = CascadeType.REMOVE)
    List<ApplyMentee> mentees = new ArrayList<>();

    @Builder
    public Mentoring(String title, String description, Integer limited, MentoringCate category, Integer count, Boolean isReceipt, User mentor) {
        this.title = title;
        this.description = description;
        this.limited = limited;
        this.category = category;
        this.count = count;
        this.isReceipt = isReceipt;
        this.mentor = mentor;
    }

    public void update(UpdateMentoringReq updateMentoringReq) {
        this.title = updateMentoringReq.getTitle();
        this.category = MentoringCate.findByLabel(updateMentoringReq.getCategory());
        this.description = updateMentoringReq.getDescription();
        this.limited = updateMentoringReq.getLimited();
    }

    public void changeReceiptToFalse() {
        this.isReceipt = false;
    }

    public void applyMentoring() {
        count += 1;
        if(count == limited) changeReceiptToFalse();
    }


    public void setMentor(User mentor) {
        this.mentor = mentor;
    }
}

