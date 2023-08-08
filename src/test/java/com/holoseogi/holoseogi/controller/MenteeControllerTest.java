package com.holoseogi.holoseogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateApplyMenteeReq;
import com.holoseogi.holoseogi.repository.ApplyMenteeRepository;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.security.jwt.JwtTokenProvider;
import com.holoseogi.holoseogi.type.MentoringCate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class MenteeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ApplyMenteeRepository menteeRepository;
    @Autowired
    private MentoringRepository mentoringRepository;

    private User loginUser;
    private String accessToken;
    private Mentoring mentoring;

    @BeforeEach
    void init() {
        authorize("user1@gmail.com");
        createMentoring();
    }

    @AfterEach
    void delete() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("POST /v1/mentee/{mentoringId} 요청시 해당 멘토링에 멘티 신청을 한다. " +
            "멘티신청시 멘토링 count도 +1 되어야한다.")
    public void applyMentee() throws Exception {
        // given
        CreateApplyMenteeReq requestDto = new CreateApplyMenteeReq("멘토링신청합니다.");
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/mentee/{mentoringId}", mentoring.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(mapper.writeValueAsString(requestDto))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()))
                .andExpect(jsonPath("$.mentoringId").value(mentoring.getId()))
                .andExpect(jsonPath("$.mentoringTitle").value(mentoring.getTitle()))
                .andExpect(jsonPath("$.applicantId").value(loginUser.getId()))
                .andExpect(jsonPath("$.applicantEmail").value(loginUser.getEmail()));

        Mentoring after = mentoringRepository.findById(mentoring.getId()).get();
        assertThat(after.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("GET /v1/mentee/{applyMenteeId} 요청시 멘티 신청 정보를 가져온다.")
    public void getMenteeInfoTest() throws Exception {
        // given
        ApplyMentee save = ApplyMentee.builder()
                .description("멘토링신청합니다.")
                .applicant(loginUser)
                .mentoring(mentoring)
                .build();
        menteeRepository.save(save);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/mentee/{applyMenteeId}", save.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(save.getDescription()))
                .andExpect(jsonPath("$.mentoringId").value(mentoring.getId()))
                .andExpect(jsonPath("$.mentoringTitle").value(mentoring.getTitle()))
                .andExpect(jsonPath("$.applicantId").value(loginUser.getId()))
                .andExpect(jsonPath("$.applicantEmail").value(loginUser.getEmail()));
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
        accessToken = jwtTokenProvider.createAccessToken(principal, authorities);
    }
}