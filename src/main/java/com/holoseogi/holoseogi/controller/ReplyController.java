package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateReplyReq;
import com.holoseogi.holoseogi.model.response.ReplyResp;
import com.holoseogi.holoseogi.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comment")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{postId}")
    public ResponseEntity<ReplyResp> createComment(@PathVariable("postId") Long postId, @RequestBody CreateReplyReq requestDto) {
        ReplyResp createdComment = replyService.createComment(postId, requestDto);
        return ResponseEntity.ok(createdComment);
    }

}