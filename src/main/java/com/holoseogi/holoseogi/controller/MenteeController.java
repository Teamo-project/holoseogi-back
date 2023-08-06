package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateApplyMenteeReq;
import com.holoseogi.holoseogi.model.response.ApplyMenteeInfoResp;
import com.holoseogi.holoseogi.service.MenteeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mentee")
public class MenteeController {

    private final MenteeService menteeService;

    @PostMapping("/{mentoringId}")
    public ResponseEntity<ApplyMenteeInfoResp> applyMentee(@PathVariable("mentoringId") Long mentoringId, @RequestBody CreateApplyMenteeReq dto) {
        Long applyMenteeId = menteeService.createApplyMentee(mentoringId, dto);
        return ResponseEntity.ok(menteeService.getApplyMenteeDtoById(applyMenteeId));
    }

}
