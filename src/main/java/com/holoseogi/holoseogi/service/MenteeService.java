package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.exception.BadRequestException;
import com.holoseogi.holoseogi.model.entity.ApplyMentee;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.entity.User;
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
        Mentoring mentoring = mentoringService.getMentoringWithMentorById(mentoringId);
        User loginUser = userService.getLoginUser();

        if (mentoring.getMentor().equals(loginUser)) {
            throw new BadRequestException("본인의 멘토링 글은 신청할 수 없습니다.");
        } else if (!mentoring.getIsReceipt()) {
            throw new BadRequestException("이미 마감된 멘토링 글입니다.");
        } else if (mentoring.getMentees().stream().anyMatch(applyMentee -> applyMentee.getApplicant().getId().equals(loginUser.getId()))){
            throw new BadRequestException("이미 신청되었습니다.");
        }

        mentoring.applyMentoring();
        ApplyMentee applyMentee = dto.toEntity(loginUser, mentoring);
        return menteeRepository.save(applyMentee).getId();
    }

    @Transactional(readOnly = true)
    public ApplyMenteeInfoResp getApplyMenteeDtoById(Long applyMenteeId) {
        ApplyMentee applyMentee = menteeRepository.findRelatedAllById(applyMenteeId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        return new ApplyMenteeInfoResp(applyMentee);
    }
}
