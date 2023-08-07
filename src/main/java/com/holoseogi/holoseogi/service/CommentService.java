package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.Board;
import com.holoseogi.holoseogi.model.entity.Comment;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CommentRequestDto;
import com.holoseogi.holoseogi.model.request.CommentResponseDto;
import com.holoseogi.holoseogi.repository.BoardRepository;
import com.holoseogi.holoseogi.repository.CommentRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long createComment(Long userId, Long boardId, CommentRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .content(requestDto.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }

    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        comment.updateContent(requestDto.getContent());
        return commentId;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        return commentRepository.findAllByBoard_Id(boardId)
                .stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentRepository.delete(comment);
    }
}
