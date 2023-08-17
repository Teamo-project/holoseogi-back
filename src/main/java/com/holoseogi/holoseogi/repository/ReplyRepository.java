package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Reply;
import com.holoseogi.holoseogi.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select r from Reply r join fetch r.creator c where r.id = :replyId")
    Optional<Reply> findById(@Param("replyId") Long replyId);

    @Query("select r from Reply r " + "where r.content like %:content%")
    Page<Reply> searchReplies(Pageable pageable, @Param("content") String content);

}