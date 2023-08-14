package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.request.SearchMentoring;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.model.response.MentoringListResp;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.service.MentoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mentoring")
public class MentoringController {

    private final MentoringService mentoringService;

    @PostMapping
    public ResponseEntity createMentoring(@RequestBody CreateMentoringReq request) {
        mentoringService.createMentoring(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{mentoringId}")
    public ResponseEntity<MentoringDetailResp> getMentoringDetail(@AuthenticationPrincipal CustomUserDetails loginUser, @PathVariable("mentoringId") Long mentoringId) {
        return ResponseEntity.ok(mentoringService.getMentoringDtoById(mentoringId, loginUser));
    }

    @PutMapping("/{mentoringId}")
    public ResponseEntity updateMentoringDetail(@PathVariable("mentoringId") Long mentoringId, @RequestBody UpdateMentoringReq request) {
        mentoringService.updateMentoringDetail(mentoringId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MentoringListResp>> getMentorings(@PageableDefault(
            size = 10,
            sort = "createDate",
            direction = Sort.Direction.DESC) Pageable pageable, SearchMentoring search) {

        return ResponseEntity.ok(mentoringService.getMentoringsDto(pageable, search));
    }

    @PatchMapping("/{mentoringId}/receipt")
    public ResponseEntity finishedReceipt(@PathVariable("mentoringId") Long mentoringId) {
        mentoringService.finishedReceipt(mentoringId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{mentoringId}")
    public void deleteMentoring(@PathVariable("mentoringId") Long mentoringId) {
        mentoringService.deleteMentoring(mentoringId);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<MentoringListResp>> myMentoringList(@PageableDefault(
            size = 10,
            sort = "createDate",
            direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(mentoringService.getMyMentoringList(pageable));
    }
}
