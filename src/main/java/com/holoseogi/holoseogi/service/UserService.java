package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getLoginUser() {
        return userRepository.findById(getLoginUserId()).orElseThrow(() -> new RuntimeException("로그인 ID에 맞는 유저가 저장되어있찌 않습니다"));
    }

    private Long getLoginUserId() {
        return Optional.ofNullable(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new RuntimeException("로그인된 유저 정보가 없습니다."));
    }
}
