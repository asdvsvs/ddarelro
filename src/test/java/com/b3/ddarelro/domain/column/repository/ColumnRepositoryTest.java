package com.b3.ddarelro.domain.column.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.repository.BoardRepository;
import com.b3.ddarelro.domain.column.ColumnCommonTest;
import com.b3.ddarelro.domain.column.entity.Column;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
class ColumnRepositoryTest implements ColumnCommonTest {

    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private EntityManager em;


    private Board saveBoard;
    private Column saveColumn;
    private Column saveAnotherColumn;
    private Column TEST_COLUMN;

    @BeforeEach
    void setUp() {
        TEST_COLUMN = Column.builder()
            .title(TEST_TITLE)
            .board(TEST_BOARD)
            .priority(TEST_ID)
            .build();
        saveBoard = boardRepository.save(TEST_BOARD);
        saveColumn = columnRepository.save(TEST_COLUMN);
        saveAnotherColumn = columnRepository.save(TEST_COLUMN2);
    }

    @Test
    @DisplayName("보드id와 일치하고 삭제되지 않은 컬럼 전체 조회 테스트")
    @Transactional
    void test1() {
        //given
        Long boardId = TEST_ID;

        //when
        List<Column> columnList = columnRepository.findAllByBoardIdAndNotDeleted(
            boardId);

        //then
        assertThat(columnList.get(0).getBoard().getId()).isEqualTo(boardId);
        assertThat(columnList.get(0).getDeleted()).isFalse();
        assertThat(columnList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("보드id와 일치하고 삭제된 컬럼id 전체 조회 테스트")
    @Transactional
    void test2() {
        //given
        Long boardId = TEST_BOARD.getId();
        //when
        ReflectionTestUtils.setField(TEST_COLUMN, "deleted", true);
        saveColumn = columnRepository.save(TEST_COLUMN);

        List<Long> columnIdList = columnRepository.findAllByBoardIdAndDeleted(boardId);

        //then
        assertThat(columnIdList.get(0)).isEqualTo(TEST_COLUMN.getId());
        assertThat(columnIdList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("보드id와 일치하는 컬럼 개수 조회 테스트")
    @Transactional
    void test3() {
        //given
        Long boardId = TEST_BOARD.getId();

        //when
        Long count = columnRepository.countByBoardId(boardId);

        //then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("컬럼 소프트 딜리트 테스트")
    void test4() {
        //given
        List<Long> columnIdList = List.of(TEST_COLUMN.getId(), TEST_COLUMN2.getId());

        //when
        columnRepository.softDelete(columnIdList);
        em.clear();
        Column column = columnRepository.findById(TEST_COLUMN.getId()).get();
        Column column2 = columnRepository.findById(TEST_COLUMN2.getId()).get();

        //then
        assertThat(column.getDeleted()).isTrue();
        assertThat(column2.getDeleted()).isTrue();
    }

    @Test
    @DisplayName("소프트 딜리트 된 컬럼 복구 테스트")
    void test5() {
        //given
        List<Long> columnIdList = List.of(TEST_COLUMN.getId(), TEST_COLUMN2.getId());

        //when
        columnRepository.softRestore(columnIdList);
        em.clear();
        Column column = columnRepository.findById(TEST_COLUMN.getId()).get();
        Column column2 = columnRepository.findById(TEST_COLUMN2.getId()).get();

        //then
        assertThat(column.getDeleted()).isFalse();
        assertThat(column2.getDeleted()).isFalse();
    }

    @Test
    @DisplayName("나머지 컬럼 이동 테스트")
    void test6() {
        //given
        Long start = 1L;
        Long end = 2L;
        Long moveDirection = 1L;

        //when
        columnRepository.moveAnotherColumns(start, end, moveDirection);
        em.clear();
        Column column = columnRepository.findById(TEST_COLUMN.getId()).get();
        Column column2 = columnRepository.findById(TEST_COLUMN2.getId()).get();

        //then
        assertThat(column.getPriority()).isEqualTo(2);
        assertThat(column2.getPriority()).isEqualTo(3);
    }
}