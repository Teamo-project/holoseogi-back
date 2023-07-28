package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.exception.BadRequestException;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.type.MentoringCate;
import com.holoseogi.holoseogi.type.UserRole;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MentoringServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private MentoringRepository mentoringRepository;
    @Autowired private MentoringService mentoringService;
    @PersistenceContext private EntityManager em;

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
        MentoringDetailResp response = mentoringService.getMentoringById(saveMentoringId);

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

    @Test
    @DisplayName("멘토링 게시글을 수정할 수 있다.")
    public void updateMentoringDetail() throws Exception {
        // given
        Mentoring before = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .isReceipt(true)
                .count(0)
                .build();
        mentoringRepository.save(before);
        UpdateMentoringReq updateReq = UpdateMentoringReq.builder()
                .title("수정된 제목")
                .description("수정된 내용")
                .category("상담")
                .limited(10)
                .build();
        em.clear();

        // when
        mentoringService.updateMentoringDetail(before.getId(), updateReq);

        // then
        Mentoring mentoring = mentoringRepository.findById(before.getId()).get();
        assertThat(mentoring.getTitle()).isEqualTo(updateReq.getTitle());
        assertThat(mentoring.getDescription()).isEqualTo(updateReq.getDescription());
        assertThat(mentoring.getCategory()).isEqualTo(MentoringCate.findByLabel(updateReq.getCategory()));
        assertThat(mentoring.getLimited()).isEqualTo(updateReq.getLimited());
    }

    @Test
    @DisplayName("count가 0이상일 경우 수정이 불가능하며 400 에러가 발생한다.")
    public void cantUpdateMentoringDetail() throws Exception {
        // given
        Mentoring before = Mentoring.builder()
                .title("법률 멘토링 모집")
                .description("필수적으로 알아둬야하는 법률 사항에 대한 멘토링")
                .limited(5)
                .category(MentoringCate.findByLabel("법률"))
                .mentor(loginUser)
                .isReceipt(true)
                .count(1) //
                .build();
        mentoringRepository.save(before);
        UpdateMentoringReq updateReq = UpdateMentoringReq.builder()
                .title("수정된 제목")
                .description("수정된 내용")
                .category("상담")
                .limited(10)
                .build();
        em.clear();

        // when & then
        Assertions.assertThrows(BadRequestException.class, () ->{
            mentoringService.updateMentoringDetail(before.getId(), updateReq);
        });
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