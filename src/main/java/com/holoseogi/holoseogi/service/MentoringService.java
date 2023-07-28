package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.exception.BadRequestException;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.type.MentoringCate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final UserService userService;
    private final MentoringRepository mentoringRepository;

    @Transactional
    public Long createMentoring(CreateMentoringReq request) {
        Mentoring createMentoring = Mentoring.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .limited(request.getLimited())
                .category(MentoringCate.findByLabel(request.getCategory()))
                .mentor(userService.getLoginUser())
                .build();

        return mentoringRepository.save(createMentoring).getId();
    }

    @Transactional(readOnly = true)
    public MentoringDetailResp getMentoringById(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        return new MentoringDetailResp(mentoring);
    }

    @Transactional
    public void updateMentoringDetail(Long mentoringId, UpdateMentoringReq request) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));

        if (!request.getLimited().equals(mentoring.getLimited()) & mentoring.getCount() > 0) {
            throw new BadRequestException("count가 0 이상일 때 limited는 수정될 수 없습니다.");
        }

        mentoring.update(request);
    }
}
