package com.b3.ddarelro.domain.column.repository;

import com.b3.ddarelro.domain.column.entity.Column;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    @Query("select c from Column c "
        + "where c.board.id = :boardId and c.deleted = false "
        + "order by c.priority")
    List<Column> findAllByBoardIdAndNotDeleted(Long boardId);

    @Query("select c.id from Column c "
        + "where c.board.id = :boardId and c.deleted = true ")
    List<Long> findAllByBoardIdAndDeleted(Long boardId);

    @Query("select count(*) from Column c "
        + "where c.board.id = :boardId")
    Long countByBoardId(Long boardId);

    @Modifying
    @Query("update Column c "
        + "set c.deleted=true "
        + "where c.id in (:columnIdList)")
    void softDelete(List<Long> columnIdList);

    @Modifying
    @Query("update Column c "
        + "set c.deleted=false "
        + "where c.id in (:columnIdList)")
    void softRestore(List<Long> columnIdList);

    @Modifying
    @Query("update Column c set c.priority = c.priority + :moveDirection "
        + "where c.priority between :start and :end")
    void moveAnotherColumns(Long start, Long end, Long moveDirection);
}
