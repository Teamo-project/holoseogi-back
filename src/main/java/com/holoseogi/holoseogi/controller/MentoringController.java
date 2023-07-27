package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateMentoringReq;
import com.holoseogi.holoseogi.model.response.CreateMentoringResp;
import com.holoseogi.holoseogi.service.MentoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/mentoring")
public class MentoringController {

    private final MentoringService mentoringService;

    @PostMapping
    public ResponseEntity<CreateMentoringResp> createMentoring(@RequestBody CreateMentoringReq request) {
        log.info("request = {}", request);
        Long mentoring_id = mentoringService.createMentoring(request);
        return ResponseEntity.ok(mentoringService.getMentoringById(mentoring_id));
    }

}
