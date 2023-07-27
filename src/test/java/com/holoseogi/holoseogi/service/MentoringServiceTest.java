package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.common.UserRole;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.response.CreateMentoringResp;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
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
class MentoringServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private MentoringRepository mentoringRepository;
    @Autowired private MentoringService mentoringService;

    private User loginUser;

    @BeforeEach
    void init() {
        authorize();
    }

    @AfterEach
    void delete() {
        mentoringRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("멘토링 모집글 생성")
    public void createMentoring() throws Exception {
        // given
        CreateMentoringReq request = CreateMentoringReq.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category("법률")
                .build();

        // when
        mentoringService.createMentoring(request);

        // then
        assertThat(mentoringRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Id를 통해 저장된 Mentoring에 대한 정보를 담은 DTO를 반환한다")
    public void getMentoringById() throws Exception {
        // given
        CreateMentoringReq request = CreateMentoringReq.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category("법률")
                .build();
        Long saveMentoringId = mentoringService.createMentoring(request);

        // when
        CreateMentoringResp response = mentoringService.getMentoringById(saveMentoringId);

        // then
        assertThat(response.getId()).isEqualTo(saveMentoringId);
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getDescription()).isEqualTo(request.getDescription());
        assertThat(response.getLimited()).isEqualTo(request.getLimited());
        assertThat(response.getCount()).isEqualTo(0);
        assertThat(response.getMentorInfo().getId()).isEqualTo(loginUser.getId());
        assertThat(response.getMentorInfo().getName()).isEqualTo(loginUser.getName());
        assertThat(response.getMentorInfo().getEmail()).isEqualTo(loginUser.getEmail());
    }
    
    private void authorize() {
        loginUser = userRepository.findByEmail("admin@gmail.com").get();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(UserRole.ADMIN.getRole().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails(loginUser.getId(), "", authorities);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, "", authorities));
    }
}