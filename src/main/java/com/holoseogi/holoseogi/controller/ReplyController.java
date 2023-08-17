package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreateReplyReq;
import com.holoseogi.holoseogi.model.request.SearchReply;
import com.holoseogi.holoseogi.model.request.UpdateReplyReq;
import com.holoseogi.holoseogi.model.response.ReplyDetailResp;
import com.holoseogi.holoseogi.model.response.ReplyListResp;
import com.holoseogi.holoseogi.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{postId}")
    public ResponseEntity<ReplyDetailResp> createReply(@PathVariable("postId") Long postId, @RequestBody CreateReplyReq requestDto) {
        ReplyDetailResp createdReply = replyService.createReply(postId, requestDto);
        return ResponseEntity.ok(createdReply);
    }

    @GetMapping("/{postId}/{replyId}")
    public ResponseEntity<ReplyDetailResp> getReply(@PathVariable("replyId") Long replyId) {
        ReplyDetailResp reply = replyService.getReplyById(replyId);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/{postId}/list")
    public ResponseEntity<Page<ReplyListResp>> getReplies(
            @PathVariable("postId") Long postId,
            @PageableDefault(
                    size = 6,
                    sort = "createDate",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReplyListResp> repliesPage = replyService.getRepliesByPostId(postId, pageable);
        return ResponseEntity.ok(repliesPage);
    }

    @PutMapping("/{postId}/{replyId}")
    public ResponseEntity<ReplyDetailResp> updateReply(
            @PathVariable("replyId") Long replyId, @RequestBody UpdateReplyReq requestDto) {
        ReplyDetailResp updatedReply = replyService.updateReply(replyId, requestDto);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{postId}/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }
}