package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.exception.BadRequestException;
import com.holoseogi.holoseogi.model.entity.Mentoring;
import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.request.SearchMentoring;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.model.response.MentoringListResp;
import com.holoseogi.holoseogi.repository.MentoringRepository;
import com.holoseogi.holoseogi.type.MentoringCate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final UserService userService;
    private final MentoringRepository mentoringRepository;

    @Transactional
    public Long createMentoring(CreateMentoringReq request) {
        Mentoring createMentoring = request.toEntity(userService.getLoginUser());
        return mentoringRepository.save(createMentoring).getId();
    }

    @Transactional(readOnly = true)
    public MentoringDetailResp getMentoringDtoById(Long mentoringId, Long loginUserId) {
        Mentoring mentoring = mentoringRepository.findWithMentorById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        return new MentoringDetailResp(mentoring, mentoring.getMentor().getId().equals(loginUserId));
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

    @Transactional(readOnly = true)
    public Page<MentoringListResp> getMentoringsDto(Pageable pageable, SearchMentoring search) {
        return mentoringRepository.searchMentorings(pageable,
                        search.getTitle(),
                        MentoringCate.findByLabel(search.getCategory()))
                .map(MentoringListResp::new);
    }

    @Transactional(readOnly = true)
    public Page<MentoringListResp> getMyMentoringList(Pageable pageable) {
        return mentoringRepository.getMyMentorings(pageable, userService.getLoginUser())
                .map(MentoringListResp::new);
    }

    @Transactional
    public void finishedReceipt(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        mentoring.changeReceiptToFalse();
    }

    @Transactional
    public void deleteMentoring(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        if (mentoring.getCount() != 0) {
            throw new BadRequestException("count가 0이상인 객체는 삭제할 수 없습니다.");
        }
        mentoringRepository.deleteById(mentoringId);
    }

    @Transactional(readOnly = true)
    public Mentoring getMentoringWithMentorById(Long mentoringId) {
        return mentoringRepository.findWithMentorById(mentoringId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
    }
}
