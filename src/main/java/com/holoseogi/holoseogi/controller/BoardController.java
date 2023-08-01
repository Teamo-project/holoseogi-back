package com.holoseogi.holoseogi.controller;

import com.holoseogi.holoseogi.model.request.BoardCreateRequestDto;
import com.holoseogi.holoseogi.model.request.BoardListResponseDto;
import com.holoseogi.holoseogi.model.request.BoardResponseDto;
import com.holoseogi.holoseogi.model.request.BoardUpdateRequestDto;
import com.holoseogi.holoseogi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posting")
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    public Long create(@RequestBody BoardCreateRequestDto requestDto) {
        return boardService.create(requestDto);
    }

    @PutMapping("/v1/posting/{posting_id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    //상세 조회
    @GetMapping("/v1/posting/{posting_id}")
    public BoardResponseDto searchById(@PathVariable Long id) {
        return boardService.searchById(id);
    }

    //조건에 맞는 게시글 조회(목록)
    @GetMapping("/v1/posting/list?title={}&content={}")
    public List<BoardListResponseDto> searchAllDesc() {
        return boardService.searchAllDesc();
    }


    @DeleteMapping("/v1/posting/{posting_id}")
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }
}




