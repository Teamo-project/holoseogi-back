package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.response.EmailVerificationResult;
import com.holoseogi.holoseogi.model.response.LoginUserResp;
import com.holoseogi.holoseogi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") String email) {
        userService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity verificationEmail(@RequestParam("email") String email,
                                            @RequestParam("code") String authCode) {
        EmailVerificationResult result = userService.verifiedCode(email, authCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}