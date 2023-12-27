package com.b3.ddarelro.domain.comment.service;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.comment.dto.request.CommentListReq;
import com.b3.ddarelro.domain.comment.dto.response.CommentListRes;
import com.b3.ddarelro.domain.comment.dto.request.CommentCreateReq;
import com.b3.ddarelro.domain.comment.dto.request.CommentUpdateReq;
import com.b3.ddarelro.domain.comment.dto.response.CommentCreateRes;
import com.b3.ddarelro.domain.comment.dto.response.CommentDeleteRes;
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

    public CommentDeleteRes deleteComment(Long commentId, Long userId) {

        Comment comment = getUserComment(commentId, userId);
        commentRepository.delete(comment);
        return CommentDeleteRes.builder().message("댓글 삭제 성공").build();
    }

    public List<CommentListRes> getComments(CommentListReq req) {
        List<Comment> comments = commentRepository.findAllByCardIdOrderByCreatedAtDesc(req.cardId());
        return comments.stream()
            .map(comment -> CommentListRes.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build())
            .toList();
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(CommentErrorCode.NOT_FOUND));
    }

    private Comment getUserComment(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new GlobalException(CommentErrorCode.INVALID_USER);
        }
        return comment;
    }
}
