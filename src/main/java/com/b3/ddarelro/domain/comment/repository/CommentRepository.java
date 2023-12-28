package com.b3.ddarelro.domain.comment.repository;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCardOrderByCreatedAtDesc(Card card);

    @Modifying
    @Query(value = "update Comment c set c.deleted = true where c.card.id in (:cardIds)")
    void SoftDelete(List<Long> cardIds);
}