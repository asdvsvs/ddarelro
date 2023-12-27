package com.b3.ddarelro.domain.comment.service;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.comment.dto.request.CommentCreateReq;
import com.b3.ddarelro.domain.comment.dto.response.CommentCreateRes;
import com.b3.ddarelro.domain.comment.entity.Comment;
import com.b3.ddarelro.domain.comment.repository.CommentRepository;
import com.b3.ddarelro.domain.user.entity.User;
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

}
