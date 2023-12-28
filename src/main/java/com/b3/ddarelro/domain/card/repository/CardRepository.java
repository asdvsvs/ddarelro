package com.b3.ddarelro.domain.card.repository;

import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.column.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByOrderByCreatedAtDesc();

    @Query("select count(*) from Card c where c.column.id = :columnId")
    Long countByColumnId(Long columnId);

    @Query(
        "select c from Card c join fetch c.column cl where cl.id = :columnId and c.deleted = false "
            + "order by c.priority")
    List<Column> findAllByColumnId(Long columnId);

    @Modifying
    @Query(value = "update Card c set c.deleted = true where c.column.id in (:columnIds)")
    void SoftDelete(List<Long> columnIds);

    @Modifying
    @Query(value = "update Card c set c.deleted = false where c.column.id in (:columnIds)")
    void restoreAll(List<Long> columnIds);
}
