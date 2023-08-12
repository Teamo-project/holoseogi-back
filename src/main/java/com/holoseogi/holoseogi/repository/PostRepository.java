package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.type.PostCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

        @Query("select p from Post p join fetch p.creator c where p.id = :postId")
        Optional<Post> findById(@Param("postId") Long postId);

    @Query("select p from Post p " +
            "where p.title like %:title% and (:category is null or p.category = :category)")
    Page<Post> searchPosts(Pageable pageable, @Param("title") String title, @Param("category") PostCate category);
}
