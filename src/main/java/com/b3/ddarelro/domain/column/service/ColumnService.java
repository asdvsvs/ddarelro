package com.b3.ddarelro.domain.column.service;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.domain.column.dto.request.ColumnCreateReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnDeleteReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnGetReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnUpdateReq;
import com.b3.ddarelro.domain.column.dto.response.ColumnCreateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnDeleteRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnUpdateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnsGetRes;
import com.b3.ddarelro.domain.column.entity.Column;
import com.b3.ddarelro.domain.column.exception.ColumnErrorCode;
import com.b3.ddarelro.domain.column.repository.ColumnRepository;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardService boardService;
    private final UserService userService;

    public ColumnCreateRes createColumn(ColumnCreateReq req, Long userId) {
        Board board = getBoardAndLeaderCheck(req.boardId(), userId);
        Long priority = columnRepository.count() + 1;

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
        List<User> boardUsers = board.getUsers();
        if (!boardUsers.contains(user)) {
            throw new GlobalException(ColumnErrorCode.INVALID_USER);
        }

        List<Column> columns = columnRepository.findAllByBoardId(board.getId());

        return columns.stream().map(column -> ColumnsGetRes.builder()
            .title(column.getTitle()).build()).toList();
    }

    @Transactional
    public ColumnUpdateRes updateColumn(Long columnId, ColumnUpdateReq req, Long userId) {
        Board board = getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = findColumn(columnId);
        column.update(req.title());

        return ColumnUpdateRes.builder()
            .title(column.getTitle())
            .build();
    }

    public ColumnDeleteRes deleteColumn(Long columnId, ColumnDeleteReq req, Long userId) {
        Board board = getBoardAndLeaderCheck(req.boardId(), userId);

        Column column = findColumn(columnId);
        columnRepository.delete(column);

        return ColumnDeleteRes.builder().build();
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
        validateLeader(board, user);
        return board;
    }

    private void validateLeader(Board board, User user) {
        List<User> boardUsers = board.getUsers();
        User boardLeader = boardUsers.stream().filter(u -> u.getAuthority().equals(LEADER))
            .findFirst();
        if (boardLeader != user) {
            throw new GlobalException(ColumnErrorCode.NOT_LEADER);
        }
    }
}
