package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.MentoringCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {

    @Query("select m from Mentoring m where m.title like %:title% and (:category is null or m.category = :category)")
    Page<Mentoring> searchMentorings(Pageable pageable, String title, MentoringCate category);

    @Query("select m from Mentoring m where m.mentor = :loginUser")
    Page<Mentoring> getMyMentorings(Pageable pageable, @Param("loginUser") User loginUser);

    @Query("select m from Mentoring m join fetch m.mentor men where m.id = :mentoringId")
    Optional<Mentoring> findWithMentorById(@Param("mentoringId") Long mentoringId);
}
