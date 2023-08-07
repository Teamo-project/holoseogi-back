package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CommentRequestDto;
import com.holoseogi.holoseogi.model.request.CommentResponseDto;
import com.holoseogi.holoseogi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/{boardId}")
    public Long createComment(
            @PathVariable Long userId,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(userId, boardId, requestDto);
    }

    @PutMapping("/{commentId}")
    public Long updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(commentId, requestDto);
    }

    @GetMapping("/{boardId}")
    public List<CommentResponseDto> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
