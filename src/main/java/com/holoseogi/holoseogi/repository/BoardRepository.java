package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByIdDesc(); //조회



}