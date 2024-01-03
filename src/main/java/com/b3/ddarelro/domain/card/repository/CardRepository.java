package com.b3.ddarelro.domain.card.repository;

import com.b3.ddarelro.domain.card.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c from Card c where c.column.id = :columnId and c.deleted = false order by c.priority")
    List<Card> findAllByColumnIdAndNotDeleted(Long columnId);

    @Query("select count(*) from Card c where c.column.id = :columnId")
    Long countByColumnId(Long columnId);

    @Modifying
    @Query(value = "update Card c set c.deleted = true where c.column.id in (:columnIds)")
    void SoftDelete(List<Long> columnIds);

    @Modifying
    @Query(value = "update Card c set c.deleted = false where c.column.id in (:columnIds)")
    void restoreAll(List<Long> columnIds);

    @Modifying
    @Query("update Card c set c.priority = c.priority + :moveDirection "
        + "where c.column.id= :columnId and c.priority between :start and :end")
    void moveAnotherCards(Long start, Long end, Long moveDirection, Long columnId);
}
