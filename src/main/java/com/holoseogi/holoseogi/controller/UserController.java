package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.response.LoginUserResp;
import com.holoseogi.holoseogi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoginUserResp> getLoginUser() {
        return ResponseEntity.ok(LoginUserResp.getLoginUserResp(userService.getLoginUser()));
    }


}