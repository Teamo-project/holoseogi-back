package com.holoseogi.holoseogi.repository;

import com.holoseogi.holoseogi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT user.refreshToken FROM User user WHERE user.id=:id")
    String getRefreshTokenById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.refreshToken=:token WHERE user.id=:id")
    void updateRefreshToken(@Param("id") Long id, @Param("token") String token);
}