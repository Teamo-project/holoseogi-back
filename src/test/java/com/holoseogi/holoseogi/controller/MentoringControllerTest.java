package com.holoseogi.holoseogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holoseogi.holoseogi.common.UserRole;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MentoringControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private UserRepository userRepository;

    private User loginUser;

    @BeforeEach
    void init() {
        authorize();
    }

    @Test
    @DisplayName("POST v1/mentoring 요청시 저장된 Mentoring객체를 반환한다")
    public void createMentoring() throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/mentoring")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer debug")
                        .content(mapper.writeValueAsString(CreateMentoringReq.builder()
                                        .title("법률 멘토링 모집")
                                        .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                                        .limited(5)
                                        .category("법률")
                                        .build()
                                )
                        )
                ).andExpect(status().isOk())
                .andDo(print());

        // when

        // then
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