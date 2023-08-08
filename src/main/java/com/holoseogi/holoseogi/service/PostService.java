package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.Post;
import com.holoseogi.holoseogi.model.request.CreatePostReq;
import com.holoseogi.holoseogi.model.request.SearchPost;
import com.holoseogi.holoseogi.model.response.PostDetailResp;
import com.holoseogi.holoseogi.model.response.PostListResp;
import com.holoseogi.holoseogi.repository.PostRepository;
import com.holoseogi.holoseogi.type.PostCate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void createPost(CreatePostReq requestDto) {
        Post createPost = requestDto.toEntity(userService.getLoginUser());
        postRepository.save(createPost);
    }

    @Transactional(readOnly = true)
    public PostDetailResp getPostDtoById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("객체를 찾을 수 없습니다."));
        return new PostDetailResp(post);
    }

    @Transactional(readOnly = true)
    public Page<PostListResp>  getPostsDto(Pageable pageable, SearchPost search) {
        return postRepository.searchPosts(pageable
                        , search.getTitle()
                        , PostCate.findByLabel(search.getCategory()))
                .map(PostListResp::new);

    }
}
