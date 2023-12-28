package com.b3.ddarelro.domain.comment.service;

import com.b3.ddarelro.domain.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentDeleteRestoreService {

    private final CommentRepository commentRepository;

    /**
     * Card가 delete할 때, 이 메소드를 호출 (상위에서 soft delete한 경우에만 카드도 soft delete를 실행)
     */
    public void deleteAllComment(List<Long> cardIds) {
        commentRepository.SoftDelete(cardIds);
    }

    /**
     * Card가 restore할 때 comment도 restore 한다.
     */
    public void restoreAllComment(List<Long> cardIds) {
        commentRepository.restoreAll(cardIds);
    }

}
