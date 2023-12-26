package com.b3.ddarelro.domain.board.controller;


import com.b3.ddarelro.domain.board.dto.response.BoardGetResDto;
import com.b3.ddarelro.domain.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardGetResDto> getBoardListAll(){ //보드 목록 조회
        return boardService.getBoardList();
    }

    @GetMapping("/boardId")
    public BoardGetResDto getBoard(
        @PathVariable Long boardId
    ){ //보드 단건 조회
        return boardService.getBoardOne(boardId);

    }
}
