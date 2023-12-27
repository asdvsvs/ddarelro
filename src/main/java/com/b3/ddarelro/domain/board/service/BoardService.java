package com.b3.ddarelro.domain.board.service;

import com.b3.ddarelro.domain.board.dto.response.BoardGetResDto;
import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    //private final UserRepository userRepository;




    public List<BoardGetResDto> getBoardList() {

        List<Board> boardList =  boardRepository.findAll();

        return boardList.stream()
            .map(BoardGetResDto::new)
            .toList();

    }

    public BoardGetResDto getBoardOne(Long boardId) {

        Board findBoard = findByBoardById(boardId);

        return new BoardGetResDto(findBoard);


    }

    private Board findByBoardById(Long id){
        return boardRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

    }



}
