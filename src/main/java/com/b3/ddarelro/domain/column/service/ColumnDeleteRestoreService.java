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
//    private final CardDeleteRestoreService cardDeleteRestoreService;

    public void deleteAllColumn(Long boardId) {
        List<Column> columns = columnRepository.findAllByBoardIdAndNotDeleted(boardId);
        List<Long> columnIdList = columns.stream().map(Column::getId).toList();
        columnRepository.softDelete(columnIdList);
//        cardDeleteRestoreService.deleteAllCard(columnIdList);
    }

    public void restoreAllColumn(Long boardId) {
        List<Long> columnIdList = columnRepository.findAllByBoardIdAndDeleted(boardId);
        columnRepository.softRestore(columnIdList);
//        cardDeleteRestoreService.restoreAllCard(columnIdList);
    }
}
