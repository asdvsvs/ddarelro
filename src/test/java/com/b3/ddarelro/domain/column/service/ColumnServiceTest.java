package com.b3.ddarelro.domain.column.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.domain.column.ColumnCommonTest;
import com.b3.ddarelro.domain.column.dto.request.ColumnCreateReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnDeleteReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnGetReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnMoveReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnRestoreReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnUpdateReq;
import com.b3.ddarelro.domain.column.dto.response.ColumnCreateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnDeleteRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnMoveRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnRestoreRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnUpdateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnsGetRes;
import com.b3.ddarelro.domain.column.repository.ColumnRepository;
import com.b3.ddarelro.domain.user.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ColumnServiceTest implements ColumnCommonTest {

    @InjectMocks
    private ColumnService columnService;
    @Mock
    private ColumnRepository columnRepository;
    @Mock
    private BoardService boardService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(TEST_COLUMN.getBoard(), "id", TEST_ID);
        ReflectionTestUtils.setField(TEST_COLUMN, "id", TEST_ID);
    }

    @Test
    @DisplayName("컬럼 작성 테스트")
    void test1() {
        //given
        ColumnCreateReq req = ColumnCreateReq.builder()
            .boardId(TEST_ID).title(TEST_TITLE).build();

        //when
        when(columnRepository.save(any())).thenReturn(TEST_COLUMN);
        ColumnCreateRes res = columnService.createColumn(req, TEST_ID);

        //then
        assertThat(res.boardId()).isEqualTo(req.boardId());
        assertThat(res.title()).isEqualTo(req.title());
        verify(columnRepository).countByBoardId(any());
        verify(columnRepository).save(any());
    }

    @Test
    @DisplayName("컬럼 목록 조회 테스트")
    void test2() {
        //given
        ColumnGetReq req = ColumnGetReq.builder()
            .boardId(TEST_ID).build();

        //when
        when(boardService.findBoard(any())).thenReturn(TEST_BOARD);
        when(columnRepository.findAllByBoardIdAndNotDeleted(any())).thenReturn(
            List.of(TEST_COLUMN));
        List<ColumnsGetRes> res = columnService.getColumns(req, TEST_ID);

        //then
        assertThat(res.get(0).title()).isEqualTo(TEST_TITLE);
    }

    @Test
    @DisplayName("컬럼 수정 테스트")
    void test3() {
        //given
        ColumnUpdateReq req = ColumnUpdateReq.builder()
            .boardId(TEST_ID).title("anther" + TEST_TITLE).build();

        //when
        when(columnRepository.findById(any())).thenReturn(Optional.of(TEST_COLUMN));
        ColumnUpdateRes res = columnService.updateColumn(TEST_ID, req, TEST_ID);

        //then
        assertThat(res.title()).isEqualTo(req.title());
    }

    @Test
    @DisplayName("컬럼 삭제 테스트")
    void test4() {
        //given
        ColumnDeleteReq req = ColumnDeleteReq.builder()
            .boardId(TEST_ID).build();

        //when
        when(columnRepository.findById(any())).thenReturn(Optional.of(TEST_COLUMN));
        ColumnDeleteRes res = columnService.deleteColumn(TEST_ID, req, TEST_ID);

        //then
        assertThat(res.title()).isEqualTo(TEST_COLUMN.getTitle());
        assertThat(res.deleted()).isEqualTo(true);
    }

    @Test
    @DisplayName("컬럼 복구 테스트")
    void test5() {
        //given
        ColumnRestoreReq req = ColumnRestoreReq.builder()
            .boardId(TEST_ID).build();

        //when
        when(columnRepository.findById(any())).thenReturn(Optional.of(TEST_COLUMN));
        ColumnRestoreRes res = columnService.restoreColumn(TEST_ID, req, TEST_ID);

        //then
        assertThat(res.title()).isEqualTo(TEST_COLUMN.getTitle());
        assertThat(res.deleted()).isEqualTo(false);
    }

    @Test
    @DisplayName("컬럼 이동 테스트")
    void test6() {
        //given
        ColumnMoveReq req = ColumnMoveReq.builder()
            .boardId(TEST_ID).move(2L).build();

        //when
        when(columnRepository.findById(any())).thenReturn(Optional.of(TEST_COLUMN));
        when(columnRepository.countByBoardId(any())).thenReturn(2L);
        ColumnMoveRes res = columnService.moveColumn(TEST_ID, req, TEST_ID);

        //then
        assertThat(res.ColumnId()).isEqualTo(TEST_COLUMN.getId());
        assertThat(res.priority()).isEqualTo(2L);
    }


}