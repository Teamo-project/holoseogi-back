package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.MentoringCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {

    @Query("select m from Mentoring m where m.title like %:title% and (:category is null or m.category = :category)")
    Page<Mentoring> searchMentorings(Pageable pageable, String title, MentoringCate category);

    @Query("select m from Mentoring m join fetch m.mentor where m.mentor.id = :loginUserId and m.id < :lastMentoringId")
    Slice<Mentoring> getMyMentorings(Pageable pageable,
                                     @Param("loginUserId") Long userId,
                                     @Param("lastMentoringId") Long lastMentoringId);

    @Query("select m from Mentoring m join fetch m.mentor men where m.id = :mentoringId")
    Optional<Mentoring> findWithMentorById(@Param("mentoringId") Long mentoringId);


    List<Mentoring> findByMentor(User user);
}
