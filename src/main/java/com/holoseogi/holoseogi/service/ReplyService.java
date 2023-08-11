package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.Reply;
import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.model.request.CreateReplyReq;
import com.holoseogi.holoseogi.model.request.SearchPost;
import com.holoseogi.holoseogi.model.request.SearchReply;
import com.holoseogi.holoseogi.model.request.UpdateReplyReq;
import com.holoseogi.holoseogi.model.response.ReplyDetailResp;
import com.holoseogi.holoseogi.model.response.ReplyListResp;
import com.holoseogi.holoseogi.repository.ReplyRepository;
import com.holoseogi.holoseogi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    @Transactional
    public ReplyDetailResp createReply(Long postId, CreateReplyReq requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        User user = userService.getLoginUser();

        Reply reply = new Reply(requestDto.getContent(), post, user);
        Reply savedReply = replyRepository.save(reply);

        return new ReplyDetailResp(savedReply);
    }
    @Transactional(readOnly = true)
    public ReplyDetailResp getReplyById(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));
        return new ReplyDetailResp(reply);
    }


    @Transactional(readOnly = true)
    public Page<ReplyListResp>  getRepliesDto(Pageable pageable, SearchReply search) {
        return replyRepository.searchReplies(pageable
                        , search.getContent())
                .map(ReplyListResp::new);

    }


    @Transactional
    public ReplyDetailResp updateReply(Long replyId, UpdateReplyReq requestDto) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        reply.update(requestDto);
        return new ReplyDetailResp(reply);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        if (!replyRepository.existsById(replyId)) {
            throw new RuntimeException("Reply not found");
        }
        replyRepository.deleteById(replyId);
    }
}

