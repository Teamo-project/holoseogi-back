package com.holoseogi.holoseogi.init;

import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.PostRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserRepository userRepository;
    private final MentoringRepository mentoringRepository;
    private final PostRepository postRepository;
    private User adminUser;

    @PostConstruct
    public void init() {
        createDebugUser(); // AdminUser 생성
        getAdminUser(); // 전역변수에 adminUser넣어두기
        createUsers(); // 일반 유저 생성
        createMentorings(); // 멘토링 글 생성
        createPostings(); // 게시글 생성
    }

    private void createDebugUser() {
        User admin = User.builder()
                .name("admin")
                .role(UserRole.MENTOR)
                .email("admin@gmail.com")
                .phone("01012345678")
                .region(UserRegion.SEOUL)
                .gender(UserGender.FEMALE)
                .age(25)
                .img("https://lh3.googleusercontent.com/a/AAcHTtexYsEPgy_IbUzA79tynRDUjzCgfabVWcyoBoJsM5R5=s96-c")
                .authProvider(AuthProvider.GOOGLE)
                .build();
        userRepository.save(admin);
    }

    private void createUsers() {
        List<User> users = IntStream.rangeClosed(1, 10).mapToObj(i -> User.builder()
                        .name("user" + i)
                        .role(UserRole.MENTEE)
                        .email("user" + i + "@gmail.com")
                        .phone("01012345678")
                        .region(UserRegion.SEOUL)
                        .age(25)
                        .gender(UserGender.MALE)
                        .img("https://lh3.googleusercontent.com/a/AAcHTtexYsEPgy_IbUzA79tynRDUjzCgfabVWcyoBoJsM5R5=s96-c")
                        .authProvider(AuthProvider.GOOGLE)
                        .build())
                .collect(Collectors.toList());
        userRepository.saveAll(users);
    }

    private void createMentorings() {
        List<Mentoring> mentorings = IntStream.rangeClosed(1, 5).mapToObj(i -> Mentoring.builder()
                        .title("멘토링 모집")
                        .description("멘토링 모집이요")
                        .mentor(adminUser)
                        .category(MentoringCate.COUNCEL)
                        .count(0)
                        .isReceipt(true)
                        .limited(5)
                        .build())
                .collect(Collectors.toList());
        mentoringRepository.saveAll(mentorings);
    }

    private void createPostings() {
        List<Post> postings = IntStream.rangeClosed(1, 20).mapToObj(i -> Post.builder()
                        .title("게시글 제목")
                        .content("게시글 내용")
                        .creator(adminUser)
                        .category(PostCate.FREE)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(postings);
    }

    private void getAdminUser() {
        adminUser = userRepository.findByEmail("admin@gmail.com").get();
    }
}
