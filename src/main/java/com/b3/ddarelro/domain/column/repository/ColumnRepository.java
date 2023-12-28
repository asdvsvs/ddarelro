package com.b3.ddarelro.domain.column.repository;

import com.b3.ddarelro.domain.column.entity.Column;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    @Query("select c from Column c "
        + "join fetch c.board b "
        + "where b.id = :boardId and c.deleted = false "
        + "order by c.priority")
    List<Column> findAllByBoardId(Long boardId);

    @Query("select count(*) from Column c "
        + "join c.board b "
        + "where b.id = :boardId")
    Long countByBoardId(Long boardId);

    @Query("update Column c "
        + "set c.deleted=true "
        + "where c.id in (:columnIdList)")
    void softDelete(List<Long> columnIdList);
}
