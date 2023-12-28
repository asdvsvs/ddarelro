package com.b3.ddarelro.domain.comment.service;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.comment.dto.request.CommentCreateReq;
import com.b3.ddarelro.domain.comment.dto.request.CommentListReq;
import com.b3.ddarelro.domain.comment.dto.request.CommentUpdateReq;
import com.b3.ddarelro.domain.comment.dto.response.CommentCreateRes;
import com.b3.ddarelro.domain.comment.dto.response.CommentDeleteRes;
import com.b3.ddarelro.domain.comment.dto.response.CommentListRes;
import com.b3.ddarelro.domain.comment.dto.response.CommentUpdateRes;
import com.b3.ddarelro.domain.comment.entity.Comment;
import com.b3.ddarelro.domain.comment.exception.CommentErrorCode;
import com.b3.ddarelro.domain.comment.repository.CommentRepository;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CardService cardService;

    /**
     * 카드가 삭제되면 -> 어차피 comment도 soft delete가 된다. (comment는 자기 스스로 soft delete 불가) 고로, card의 삭제여부는
     * 체크하지 않고 comment의 삭제여부만 체크하면 된다. 생성할 때는 findCard에서 체크해주기 때문에 체크할 필요 없음
     */

    public CommentCreateRes createComment(CommentCreateReq req, Long userId) {
        User user = userService.findUser(userId);
        Card card = cardService.findCard(req.cardId());
        Comment comment = Comment.builder().content(req.content()).card(card).user(user).build();
        commentRepository.save(comment);
        return CommentCreateRes.builder().id(comment.getId()).content(comment.getContent()).build();
    }

    public CommentUpdateRes updateComment(Long commentId, CommentUpdateReq req, Long userId) {
        Comment comment = getUserComment(commentId, userId);
        comment.update(req.content());
        return CommentUpdateRes.builder().id(comment.getId()).content(comment.getContent()).build();
    }

    /**
     * 댓글의 자체 삭제는 soft delete 기능이 없다. 무조건 hard delete
     */
    public CommentDeleteRes deleteComment(Long commentId, Long userId) {
        Comment comment = getUserComment(commentId, userId);
        commentRepository.delete(comment);
        return CommentDeleteRes.builder().message("댓글 삭제 성공").build();
    }

    public List<CommentListRes> getComments(CommentListReq req) {
        Card card = cardService.findCard(req.cardId());
        List<Comment> comments = commentRepository.findAllByCardOrderByCreatedAtDesc(card);
        return comments.stream()
            .map(comment -> CommentListRes.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname())
                .build())
            .toList();
    }

    /**
     * Card가 delete할 때, 이 메소드를 호출 (상위에서 soft delete한 경우에만 카드도 soft delete를 실행)
     */
    public void deleteAllComment(List<Long> cardIds) {
        commentRepository.SoftDelete(cardIds);
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(CommentErrorCode.NOT_FOUND));
        deleteCheck(comment);
        return comment;
    }

    private Comment getUserComment(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new GlobalException(CommentErrorCode.INVALID_USER);
        }
        return comment;
    }

    private void deleteCheck(Comment comment) {
        if (comment.getDeleted()) {
            throw new GlobalException(CommentErrorCode.NOT_FOUND);
        }
    }
}
