package com.b3.ddarelro.domain.checklist.repository;

import com.b3.ddarelro.domain.checklist.entity.CheckList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    List<CheckList> findAllByCardId(Long id);

    @Modifying
    @Query(value = "update CheckList c set c.deleted = true where c.card.id in (:cardIds)")
    void SoftDelete(List<Long> cardIds);

    @Modifying
    @Query(value = "update CheckList c set c.deleted = false where c.card.id in (:cardIds)")
    void restoreAll(List<Long> cardIds);
}
