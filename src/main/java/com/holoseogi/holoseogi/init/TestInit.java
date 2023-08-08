package com.holoseogi.holoseogi.init;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.type.AuthProvider;
import com.holoseogi.holoseogi.type.MentoringCate;
import com.holoseogi.holoseogi.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserRepository userRepository;
    private final MentoringRepository mentoringRepository;

    @PostConstruct
    public void init() {
        createDebugUser(); // AdminUser 생성
        createUsers(); // 일반 유저 생성
        createMentorings(); // 멘토링 글 생성
    }

    private void createDebugUser() {
        User admin = User.builder()
                .name("admin")
                .role(UserRole.ADMIN)
                .email("admin@gmail.com")
                .img("https://lh3.googleusercontent.com/a/AAcHTtexYsEPgy_IbUzA79tynRDUjzCgfabVWcyoBoJsM5R5=s96-c")
                .authProvider(AuthProvider.GOOGLE)
                .build();
        userRepository.save(admin);
    }

    private void createUsers() {
        IntStream.rangeClosed(1, 10).mapToObj(i -> User.builder()
                        .name("user" + i)
                        .role(UserRole.USER)
                        .email("user" + i + "@gmail.com")
                        .img("https://lh3.googleusercontent.com/a/AAcHTtexYsEPgy_IbUzA79tynRDUjzCgfabVWcyoBoJsM5R5=s96-c")
                        .authProvider(AuthProvider.GOOGLE)
                        .build())
                .forEach(user -> userRepository.save(user));
    }

    private void createMentorings() {
        IntStream.rangeClosed(1, 5).mapToObj(i -> Mentoring.builder()
                        .title("멘토링 모집")
                        .description("학교생활 상담 멘티들을 모집합니다.")
                        .mentor(userRepository.findByEmail("admin@gmail.com").get())
                        .category(MentoringCate.COUNCEL)
                        .count(0)
                        .isReceipt(true)
                        .limited(5)
                        .build())
                .forEach(mentoring -> mentoringRepository.save(mentoring));
    }
}
