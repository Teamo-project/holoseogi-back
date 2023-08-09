package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.CreatePostReq;
import com.holoseogi.holoseogi.model.request.SearchPost;
import com.holoseogi.holoseogi.model.request.UpdatePostReq;
import com.holoseogi.holoseogi.model.response.PostDetailResp;
import com.holoseogi.holoseogi.model.response.PostListResp;
import com.holoseogi.holoseogi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posting")
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestBody CreatePostReq requestDto) {
        postService.createPost(requestDto);
    }

    @GetMapping("/{postingId}")
    public ResponseEntity<PostDetailResp> getPostDetail(@PathVariable("postingId") Long postId) {
        return ResponseEntity.ok(postService.getPostDtoById(postId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostListResp>> getPostings(@PageableDefault(
            size = 10,
            sort = "createDate",
            direction = Sort.Direction.DESC) Pageable pageable, SearchPost searchPost) {
        return ResponseEntity.ok(postService.getPostsDto(pageable, searchPost));
    }

    @PutMapping("/{postingId}")
    public void updateBoard(@PathVariable("postingId") Long postId, @RequestBody UpdatePostReq requestDto) {
        postService.updatePostDetail(postId, requestDto);
    }

    @DeleteMapping("/{postingId}")
    public void deleteBoard(@PathVariable("postingId") Long postId) {
        postService.deletePost(postId);
    }
}
