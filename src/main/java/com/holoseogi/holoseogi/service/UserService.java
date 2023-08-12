package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateUserReq;
import com.holoseogi.holoseogi.model.response.EmailVerificationResult;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RedisService redisService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Transactional
    public void joinGeneralUser(CreateUserReq dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> new RuntimeException("이미 " + user.getEmail() + "의 이메일이 존재합니다."));
        userRepository.save(dto.toEntity());
    }

    @Transactional
    public User getLoginUser() {
        return userRepository.findById(this.getLoginUserId()).orElseThrow(() -> new RuntimeException("로그인 ID에 맞는 유저가 저장되어있찌 않습니다"));
    }

    private Long getLoginUserId() {
        return Optional.ofNullable(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new RuntimeException("로그인된 유저 정보가 없습니다."));
    }

    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "HOLO 이메일 인증 번호";
        String authCode = this.createCode();
        String content = "인증코드를 입력해주세요 \n\n" + authCode;
        mailService.sendEmail(toEmail, title, content);
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 이메일입니다." + user.getEmail());
        });
    }

    private String createCode() {
        int length = 6;
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info("MemberService.createCode() exception 발생");
            throw new RuntimeException("이메일 난수 생성 중 알고리즘 오류");
        }
    }


    public EmailVerificationResult verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        log.info("redisService.checkExistsValue(redisAuthCode) = {}", redisService.checkExistsValue(redisAuthCode));
        log.info("redisAuthCode.equal = {}", redisAuthCode.equals(authCode));
        log.info("authcode = {}, redisAuthCode = {}", authCode, redisAuthCode);
        return new EmailVerificationResult(authResult);
    }
}
