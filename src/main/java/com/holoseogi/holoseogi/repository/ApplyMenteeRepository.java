package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplyMenteeRepository extends JpaRepository<ApplyMentee, Long> {

    @Query("select m from ApplyMentee m " +
            "join fetch m.applicant a " +
            "join fetch  m.mentoring mt " +
            "where m.id = :applyMenteeId")
    Optional<ApplyMentee> findRelatedAllById(@Param("applyMenteeId") Long applyMenteeId);

    @Query("select m from ApplyMentee m " +
            "join fetch m.mentoring i " +
            "where m.applicant.id = :userId " +
            "and m.id < :lastApplicantId")
    Slice<ApplyMentee> findMyApplyMentoring(Pageable pageable,
                                            @Param("userId") Long userId,
                                            @Param("lastApplicantId") Long lastApplicantId);
}