package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.Reply;
import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateReplyReq;
import com.holoseogi.holoseogi.model.response.ReplyResp;
import com.holoseogi.holoseogi.repository.CommentRepository;
import com.holoseogi.holoseogi.repository.PostRepository;
import com.holoseogi.holoseogi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    @Transactional
    public ReplyResp createComment(Long postId, CreateReplyReq requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userService.getLoginUser();

        Reply reply = new Reply(requestDto.getContent(), post, user);
        Reply savedReply = commentRepository.save(reply);

        return new ReplyResp(savedReply);
    }
}

