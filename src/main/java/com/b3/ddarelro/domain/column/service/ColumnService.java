package com.b3.ddarelro.domain.column.service;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.domain.card.service.CardDeleteRestoreService;
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
import com.b3.ddarelro.domain.column.entity.Column;
import com.b3.ddarelro.domain.column.exception.ColumnErrorCode;
import com.b3.ddarelro.domain.column.repository.ColumnRepository;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.service.UserService;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardService boardService;
    private final UserService userService;
    private final CardDeleteRestoreService cardDeleteRestoreService;

    public ColumnCreateRes createColumn(ColumnCreateReq req, Long userId) {
        Board board = getBoardAndLeaderCheck(req.boardId(), userId);
        Long priority = columnRepository.countByBoardId(req.boardId()) + 1;

        Column column = columnRepository.save(Column.builder()
            .title(req.title())
            .board(board)
            .priority(priority)
            .build());

        return ColumnCreateRes.builder()
            .boardId(column.getBoard().getId())
            .columnId(column.getId())
            .title(column.getTitle())
            .build();
    }

    public List<ColumnsGetRes> getColumns(ColumnGetReq req, Long userId) {
        Board board = boardService.findBoard(req.boardId());
        User user = userService.findUser(userId);

        boardService.validateMember(user, board);

        List<Column> columns = columnRepository.findAllByBoardIdAndNotDeleted(board.getId());

        return columns.stream().map(column -> ColumnsGetRes.builder()
            .columnId(column.getId()).priority(column.getPriority()).title(column.getTitle())
            .build()).toList();
    }

    @Transactional
    public ColumnUpdateRes updateColumn(Long columnId, ColumnUpdateReq req, Long userId) {
        getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = findColumn(columnId);
        column.update(req.title());

        return ColumnUpdateRes.builder()
            .title(column.getTitle())
            .build();
    }

    @Transactional
    public ColumnDeleteRes deleteColumn(Long columnId, ColumnDeleteReq req, Long userId) {
        getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = findColumn(columnId);
        column.delete();
        cardDeleteRestoreService.deleteAllCard(List.of(columnId));

        return ColumnDeleteRes.builder()
            .title(column.getTitle())
            .deleted(column.getDeleted())
            .build();
    }

    @Transactional
    public ColumnRestoreRes restoreColumn(Long columnId, ColumnRestoreReq req, Long userId) {
        getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = columnRepository.findById(columnId)
            .orElseThrow(() -> new GlobalException(ColumnErrorCode.NOT_FOUND));
        column.restore();
        cardDeleteRestoreService.restoreAllCard(List.of(columnId));

        return ColumnRestoreRes.builder()
            .title(column.getTitle())
            .deleted(column.getDeleted())
            .build();
    }

    @Transactional
    public ColumnMoveRes moveColumn(Long columnId, ColumnMoveReq req, Long userId) {
        getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = findColumn(columnId);

        changePriority(req.move(), column, req.boardId());

        return ColumnMoveRes.builder()
            .ColumnId(column.getId())
            .priority(column.getPriority())
            .title(column.getTitle())
            .build();
    }

    private void changePriority(Long move, Column column, Long boardId) {
        move = convertMove(move, boardId);
        if (Objects.equals(column.getPriority(), move)) {
            throw new GlobalException(ColumnErrorCode.CANNOT_BE_SAME_PRIORITY);
        }
        Long moveDirection = column.getPriority() > move ? 1L : -1L;
        Long start = moveDirection == -1L ? column.getPriority() : move;
        Long end = moveDirection == -1L ? move : column.getPriority();

        columnRepository.moveAnotherColumns(start, end, moveDirection);
        column.move(move);
    }

    private Long convertMove(Long move, Long boardId) {
        Long columnCount = columnRepository.countByBoardId(boardId);
        if (move < 1) {
            move = 1L;
        } else if (move > columnCount) {
            move = columnCount;
        }
        return move;
    }

    public Column findColumn(Long columnId) {
        Column column = columnRepository.findById(columnId)
            .orElseThrow(() -> new GlobalException(ColumnErrorCode.NOT_FOUND));
        if (column.getDeleted()) {
            throw new GlobalException(ColumnErrorCode.IS_DELETED);
        }
        return column;
    }

    private Board getBoardAndLeaderCheck(Long boardId, Long userId) {
        Board board = boardService.findBoard(boardId);
        User user = userService.findUser(userId);
        boardService.validteUserAuthority(user, board);
        return board;
    }
}
