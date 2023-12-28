package com.b3.ddarelro.domain.column.service;

import com.b3.ddarelro.domain.column.entity.Column;
import com.b3.ddarelro.domain.column.repository.ColumnRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnDeleteRestoreService {

    private final ColumnRepository columnRepository;
    private final CardDeleteRestoreService cardDeleteRestoreService;

    public void deleteAllColumn(Long boardId) {
        List<Column> columns = columnRepository.findAllByBoardId(boardId);
        List<Long> columnIdList = columns.stream().map(Column::getId).toList();
        columns.forEach(Column::delete);
        cardDeleteRestoreService.deleteAllCard(columnIdList);
    }
}
