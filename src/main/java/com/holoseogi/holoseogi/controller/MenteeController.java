package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateApplyMenteeReq;
import com.holoseogi.holoseogi.model.response.ApplyMenteeInfoResp;
import com.holoseogi.holoseogi.model.response.MyPageMentoringListResp;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.service.MenteeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mentee")
public class MenteeController {

    private final MenteeService menteeService;

    @PostMapping("/{mentoringId}")
    public ResponseEntity applyMentee(@PathVariable("mentoringId") Long mentoringId, @RequestBody CreateApplyMenteeReq dto) {
        Long applyMenteeId = menteeService.createApplyMentee(mentoringId, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{applyMenteeId}")
    public ResponseEntity<ApplyMenteeInfoResp> getMenteeInfo(@PathVariable("applyMenteeId") Long applyMenteeId) {
        return ResponseEntity.ok(menteeService.getApplyMenteeDtoById(applyMenteeId));
    }

    @GetMapping("/my")
    public ResponseEntity<Slice<MyPageMentoringListResp>> getMyApplyMentoring(@AuthenticationPrincipal CustomUserDetails loginUser,
                                                                              @PageableDefault(
                                                                                size = 3,
                                                                                sort = "createDate",
                                                                                direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "last", required = false) Long lastApplicantId) {
        return ResponseEntity.ok(menteeService.getMyApplyMentoring(loginUser.getId(), pageable, lastApplicantId));
    }
}
