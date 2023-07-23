package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.security.jwt.ReisueAccessToken;
import com.holoseogi.holoseogi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<ReisueAccessToken> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> accessTokenMap) {

        return ResponseEntity.ok().body(
                new ReisueAccessToken(authService.refreshToken(request, response, accessTokenMap.get("accessToken"))) // json이 그대로 들어와서 Map으로 변경
        );
    }
}