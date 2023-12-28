package com.b3.ddarelro.domain.board.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.Color;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("Repository테스트 저장")
    void test() {

        //given
        Board board = Board.builder()
            .name("보드1")
            .color(Color.BLACK)
            .description("테스트용 보드입니다.")
            .build();



        boardRepository.save(board);


        // When
        Optional<Board> foundBoard = boardRepository.findByName("보드1");

        // Then
        assertTrue(foundBoard.isPresent());
        assertEquals(foundBoard.get().getColor(),board.getColor());
    }





}
