package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.*;
import com.holoseogi.holoseogi.model.response.*;
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

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody CreateUserReq dto) {
        userService.joinGeneralUser(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenResp> login(@RequestBody UserLoginReq dto) {
        LoginTokenResp LoginToken = userService.loginGeneralUser(dto);
        return ResponseEntity.ok(LoginToken);
    }

    @PutMapping("/update")
    public ResponseEntity updateUserInfo(@RequestBody UpdateUserInfoReq updateUserInfoReq) {
        userService.updateUserInfo(updateUserInfoReq);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        userService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }
}