package com.holoseogi.holoseogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.type.MentoringCate;
import com.holoseogi.holoseogi.type.UserRole;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MentoringControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MentoringRepository mentoringRepository;

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
    @DisplayName("POST v1/mentoring 요청시 mentoring객체를 저장하고, 저장된 객체를 반환한다")
    public void createMentoring() throws Exception {
        // given
        CreateMentoringReq request = CreateMentoringReq.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category("법률")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/mentoring")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer debug")
                        .content(mapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.limited").value(request.getLimited()))
                .andExpect(jsonPath("$.category").value(request.getCategory()))
                .andExpect(jsonPath("$.isReceipt").value(true))
                .andExpect(jsonPath("$.mentorInfo.email").value(loginUser.getEmail()))
                .andDo(print());
    }

    @Test
    @DisplayName("GET v1/mentoring/{mentoringId} 요청시 저장된 Mentoring객체를 반환한다.")
    public void getMentoringDetail() throws Exception {
        // given
        Mentoring save = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .build();
        mentoringRepository.save(save);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/mentoring/{mentoringId}", save.getId())
                        .header("Authorization", "Bearer debug")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(save.getTitle()))
                .andExpect(jsonPath("$.description").value(save.getDescription()))
                .andExpect(jsonPath("$.limited").value(save.getLimited()))
                .andExpect(jsonPath("$.category").value(save.getCategory().getLabel()))
                .andExpect(jsonPath("$.isReceipt").value(true))
                .andExpect(jsonPath("$.mentorInfo.email").value(loginUser.getEmail()))
                .andDo(print());
    }

    @Test
    @DisplayName("PUT v1/mentoring/{mentoringId} 요청시 Mentoring객체를 수정한다.")
    public void updateMentoringDetail() throws Exception {
        // given
        Mentoring save = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .count(0)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .build();
        mentoringRepository.save(save);

        UpdateMentoringReq updateReq = UpdateMentoringReq.builder()
                .title("수정된 제목")
                .category("상담")
                .limited(10)
                .description("수정된 설명")
                .build();
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/mentoring/{mentoringId}", save.getId())
                        .header("Authorization", "Bearer debug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateReq))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updateReq.getTitle()))
                .andExpect(jsonPath("$.category").value(updateReq.getCategory()))
                .andExpect(jsonPath("$.limited").value(updateReq.getLimited()))
                .andExpect(jsonPath("$.description").value(updateReq.getDescription()));
    }

    @Test
    @DisplayName("PUT v1/mentoring/{mentoringId} 요청시 기존에 count가 1이상이면 400에러가 발생한다")
    public void cantUpdateMentoringDetail() throws Exception {
        // given
        Mentoring save = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .count(3)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .build();
        mentoringRepository.save(save);

        UpdateMentoringReq updateReq = UpdateMentoringReq.builder()
                .title("수정된 제목")
                .category("상담")
                .limited(10)
                .description("수정된 설명")
                .build();
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/mentoring/{mentoringId}", save.getId())
                        .header("Authorization", "Bearer debug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateReq))
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET v1/mentoring/list 요청시 mentoring 게시글을 검색해서 페이지별로 볼 수 있다.")
    public void getMentorings() throws Exception {
        // given
        List<Mentoring> mentorings_raw = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Mentoring.builder()
                        .title("법률 모집 " + i)
                        .description("설명 " + i)
                        .limited(5)
                        .category(MentoringCate.findByLabel("법률"))
                        .mentor(loginUser)
                        .isReceipt(true)
                        .count(0)
                        .build())
                .collect(Collectors.toList());
        mentoringRepository.saveAll(mentorings_raw);
        List<Mentoring> mentorings = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Mentoring.builder()
                        .title("상담 모집 " + i)
                        .description("설명 " + i)
                        .limited(5)
                        .category(MentoringCate.findByLabel("상담"))
                        .mentor(loginUser)
                        .isReceipt(true)
                        .count(0)
                        .build())
                .collect(Collectors.toList());
        mentoringRepository.saveAll(mentorings);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/mentoring/list?page=2&size=5&category=법률"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", Matchers.is(5)))
                .andExpect(jsonPath("$.content[0].category").value("법률"));
    }

    @Test
    @DisplayName("PATCH /v1/mentoring/{mentoringId}/receipt mentoring의 접수 상태를 변경한다.")
    public void finishedReceipt() throws Exception {
        // given
        Mentoring save = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .count(3)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .build();
        mentoringRepository.save(save);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/mentoring/" + save.getId() + "/receipt")
                        .header("Authorization", "Bearer debug")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
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