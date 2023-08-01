package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.request.SearchMentoring;
import com.holoseogi.holoseogi.model.request.UpdateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.model.response.MentoringListResp;
import com.holoseogi.holoseogi.service.MentoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/mentoring")
public class MentoringController {

    private final MentoringService mentoringService;

    @PostMapping
    public ResponseEntity<MentoringDetailResp> createMentoring(@RequestBody CreateMentoringReq request) {
        log.info("request = {}", request);
        Long mentoringId = mentoringService.createMentoring(request);
        return ResponseEntity.ok(mentoringService.getMentoringById(mentoringId));
    }

    @GetMapping("/{mentoringId}")
    public ResponseEntity<MentoringDetailResp> getMentoringDetail(@PathVariable("mentoringId") Long mentoringId) {
        log.info("mentoringId = {}", mentoringId);
        return ResponseEntity.ok(mentoringService.getMentoringById(mentoringId));
    }

    @PutMapping("/{mentoringId}")
    public ResponseEntity<MentoringDetailResp> updateMentoringDetail(@PathVariable("mentoringId") Long mentoringId, @RequestBody UpdateMentoringReq request) {
        log.info("mentoringId = {}", mentoringId);
        mentoringService.updateMentoringDetail(mentoringId, request);
        return ResponseEntity.ok(mentoringService.getMentoringById(mentoringId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<MentoringListResp>> getMentorings(@PageableDefault(
            size = 10,
            sort = "createDate",
            direction = Sort.Direction.DESC) Pageable pageable, SearchMentoring search) {

        return ResponseEntity.ok(mentoringService.getMentorings(pageable, search));
    }

    @PatchMapping("/{mentoringId}/receipt")
    public ResponseEntity<MentoringDetailResp> finishedReceipt(@PathVariable("mentoringId") Long mentoringId) {
        mentoringService.finishedReceipt(mentoringId);
        return ResponseEntity.ok(mentoringService.getMentoringById(mentoringId));
    }
}
