package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.response.MentoringDetailResp;
import com.holoseogi.holoseogi.service.MentoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
