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

    @PutMapping("/{postingId}")
    public Long update(@PathVariable("postingId") Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    //상세 조회
    @GetMapping("/{postingId}")
    public BoardResponseDto searchById(@PathVariable("postingId") Long id) {
        return boardService.searchById(id);
    }

    //조건에 맞는 게시글 조회(목록)
    @GetMapping("/list?title={}&content={}")
    public List<BoardListResponseDto> searchAllDesc() {
        return boardService.searchAllDesc();
    }


    @DeleteMapping("/{postingId}")
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }
}




