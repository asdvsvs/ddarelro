package com.b3.ddarelro.domain.comment.repository;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.user where c.card = :card order by c.createdAt desc")
    List<Comment> findAllByFetchJoinUser(Card card);

    @Modifying
    @Query(value = "update Comment c set c.deleted = true where c.card.id in (:cardIds)")
    void SoftDelete(List<Long> cardIds);

    @Modifying
    @Query(value = "update Comment c set c.deleted = false where c.card.id in (:cardIds)")
    void restoreAll(List<Long> cardIds);
}