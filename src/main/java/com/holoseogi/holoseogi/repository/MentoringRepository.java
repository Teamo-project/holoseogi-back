package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.type.MentoringCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {

    @Query("select m from Mentoring m where m.title like %:title% and (:category is null or m.category = :category)")
    Page<Mentoring> searchMentorings(Pageable pageable, String title, MentoringCate category);

}
