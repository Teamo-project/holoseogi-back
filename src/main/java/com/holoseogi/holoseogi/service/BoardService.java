package com.holoseogi.holoseogi.service;

import com.holoseogi.holoseogi.model.entity.Board;
import com.holoseogi.holoseogi.model.request.BoardCreateRequestDto;
import com.holoseogi.holoseogi.model.request.BoardListResponseDto;
import com.holoseogi.holoseogi.model.request.BoardResponseDto;
import com.holoseogi.holoseogi.model.request.BoardUpdateRequestDto;
import com.holoseogi.holoseogi.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public Long create(BoardCreateRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity(userService.getLoginUser())).getId();
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory());

        return id;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto searchById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardListResponseDto> searchAllDesc() {
        return boardRepository.findAllByOrderByIdDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        boardRepository.delete(board);
    }

    public void deleteBoard(Long id) {
    }
}
