package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Reply, Long> {

}
