package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateApplyMenteeReq;
import com.holoseogi.holoseogi.model.response.ApplyMenteeInfoResp;
import com.holoseogi.holoseogi.repository.ApplyMenteeRepository;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.type.MentoringCate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenteeServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private MentoringRepository mentoringRepository;
    @Autowired private MenteeService menteeService;
    @Autowired private ApplyMenteeRepository menteeRepository;

    private User loginUser;
    private Mentoring mentoring;

    @BeforeEach
    void init() {
        createMentoring();
        authorize("user1@gmail.com");
    }

    @AfterEach
    void delete() {
        menteeRepository.deleteAll(); // 멘티 먼저 삭제해야한다.
        mentoringRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("멘토링 신청")
    public void applyMentee() throws Exception {
        // given
        CreateApplyMenteeReq createApplyMenteeReq = new CreateApplyMenteeReq("해당 멘토링에 관심있습니다.");

        // when
        Long applyMenteeId = menteeService.createApplyMentee(mentoring.getId(), createApplyMenteeReq);

        // then
        ApplyMentee applyMentee = menteeRepository.findById(applyMenteeId).get();
        Mentoring afterMentoring = mentoringRepository.findById(mentoring.getId()).get();
        assertThat(applyMentee.getApplicant().getId()).isEqualTo(loginUser.getId());
        assertThat(applyMentee.getDescription()).isEqualTo(createApplyMenteeReq.getDescription());
        assertThat(afterMentoring.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("멘토링 신청 정보 가져오기")
    public void getApplyMenteeDtoByIdTest() throws Exception {
        // given
        ApplyMentee save = ApplyMentee.builder()
                .mentoring(mentoring)
                .applicant(loginUser)
                .description("신청합니다")
                .build();
        menteeRepository.save(save);

        // when
        ApplyMenteeInfoResp resultDto = menteeService.getApplyMenteeDtoById(save.getId());

        // then
        assertThat(resultDto.getDescription()).isEqualTo(save.getDescription());
        assertThat(resultDto.getMentoringId()).isEqualTo(mentoring.getId());
        assertThat(resultDto.getMentoringTitle()).isEqualTo(mentoring.getTitle());
        assertThat(resultDto.getApplicantEmail()).isEqualTo(loginUser.getEmail());
        assertThat(resultDto.getApplicantId()).isEqualTo(loginUser.getId());

    }
    
    private void createMentoring() {
        mentoring = Mentoring.builder()
                .title("학교폭력 멘토링 모집")
                .description("학교폭력 상담 멘티들을 모집합니다.")
                .mentor(userRepository.findByEmail("admin@gmail.com").get())
                .category(MentoringCate.LAW)
                .count(0)
                .isReceipt(true)
                .limited(5)
                .build();
        mentoringRepository.save(mentoring);
    }

    private void authorize(String userEmail) {
        loginUser = userRepository.findByEmail(userEmail).get();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(loginUser.getRole().getRole().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails(loginUser.getId(), "", authorities);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, "", authorities));
    }
}