package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.OAuth2JoinPlusUserInfo;
import com.holoseogi.holoseogi.security.jwt.ReissueAccessToken;
import com.holoseogi.holoseogi.service.AuthService;
import com.holoseogi.holoseogi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<ReissueAccessToken> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> accessTokenMap) {

        return ResponseEntity.ok().body(
                new ReissueAccessToken(authService.refreshToken(request, response, accessTokenMap.get("accessToken"))) // json이 그대로 들어와서 Map으로 변경
        );
    }

    @PostMapping("/join/plus")
    public ResponseEntity plusOAuth2UserInfo(@RequestBody OAuth2JoinPlusUserInfo dto) {
        userService.updateOauth2UserInfo(dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}