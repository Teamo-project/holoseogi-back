package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.request.CreateApplyMenteeReq;
import com.holoseogi.holoseogi.model.response.ApplyMenteeInfoResp;
import com.holoseogi.holoseogi.repository.ApplyMenteeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenteeService {

    private final ApplyMenteeRepository menteeRepository;
    private final UserService userService;
    private final MentoringService mentoringService;

    @Transactional
    public Long createApplyMentee(Long mentoringId, CreateApplyMenteeReq dto) {
        Mentoring mentoring = mentoringService.getMentoringById(mentoringId);
        mentoring.applyMentoring();

        ApplyMentee applyMentee = dto.toEntity(userService.getLoginUser(), mentoring);
        return menteeRepository.save(applyMentee).getId();
    }

    @Transactional(readOnly = true)
    public ApplyMenteeInfoResp getApplyMenteeDtoById(Long applyMenteeId) {
        ApplyMentee applyMentee = menteeRepository.findRelatedAllById(applyMenteeId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        return new ApplyMenteeInfoResp(applyMentee);
    }
}
