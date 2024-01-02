package com.b3.ddarelro.domain.column;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.column.entity.Column;

public interface ColumnCommonTest {

    Long TEST_ID = 1L;
    String TEST_TITLE = "testTitle";
    String TEST_NAME = "testName";
    Board TEST_BOARD = Board.builder()
        .name(TEST_NAME)
        .build();
    //    Column TEST_COLUMN = Column.builder()
//        .title(TEST_TITLE)
//        .board(TEST_BOARD)
//        .priority(TEST_ID)
//        .build();
    Column TEST_COLUMN2 = Column.builder()
        .title(TEST_TITLE)
        .board(TEST_BOARD)
        .priority(TEST_ID + 1)
        .build();
}
