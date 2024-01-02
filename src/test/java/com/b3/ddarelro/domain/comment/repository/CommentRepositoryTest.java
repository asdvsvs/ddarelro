package com.b3.ddarelro.domain.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.repository.CardRepository;
import com.b3.ddarelro.domain.comment.entity.Comment;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import com.b3.ddarelro.global.config.JpaConfig;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import(JpaConfig.class)
class CommentRepositoryTest {

    User user;

    Card card1;
    Card card2;

    Comment comment1;
    Comment comment2;

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        user = User.builder()
            .email("abc@naver.com")
            .username("abc")
            .password("1234")
            .build();
        card1 = Card.builder()
            .color("BLUE")
            .description("des")
            .name("name")
            .build();
        card2 = Card.builder()
            .color("BLUE")
            .description("des2")
            .name("name2")
            .build();
        userRepository.save(user);
        cardRepository.save(card1);
        cardRepository.save(card2);

    }

    @Test
    @Transactional
    void 카드_id_목록을_받아서_카드에_속한_댓글을_논리삭제_시킬_수_있다() {
        // given
        Comment comment1 = Comment.builder()
            .user(user)
            .card(card1)
            .content("논리삭제1")
            .build();
        Comment comment2 = Comment.builder()
            .user(user)
            .card(card2)
            .content("논리삭제2")
            .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        Long id1 = comment1.getId();
        Long id2 = comment2.getId();

        // when
        commentRepository.softDelete(List.of(card1.getId(), card2.getId()));
        em.clear();
        Comment result1 = commentRepository.findById(id1).get();
        Comment result2 = commentRepository.findById(id2).get();

        // then
        assertThat(result1.getDeleted()).isTrue();
        assertThat(result2.getDeleted()).isTrue();
    }

    @Test
    @Transactional
    void 카드_id_목록을_받아서_카드에_속한_논리삭제가_된_댓글을_복구할_수_있다() {
        // given
        Comment comment1 = Comment.builder()
            .user(user)
            .card(card1)
            .content("댓글 1")
            .build();
        Comment comment2 = Comment.builder()
            .user(user)
            .card(card2)
            .content("댓글 2")
            .build();
        ReflectionTestUtils.setField(comment1, "deleted", true);
        ReflectionTestUtils.setField(comment2, "deleted", true);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        em.clear();

        // when
        commentRepository.restoreAll(List.of(1L, 2L));
        Comment result1 = commentRepository.findById(1L).get();
        Comment result2 = commentRepository.findById(2L).get();

        // then
        assertThat(result1.getDeleted()).isFalse();
        assertThat(result2.getDeleted()).isFalse();
    }

    @Test
    @Transactional
    void 댓글_조회는_최신순으로_조회가_된다() {
        // given
        Comment comment1 = Comment.builder()
            .user(user)
            .card(card1)
            .content("댓글 1")
            .build();
        Comment comment2 = Comment.builder()
            .user(user)
            .card(card1)
            .content("댓글 2")
            .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        em.clear();

        // when
        List<Comment> comments = commentRepository.findAllByFetchJoinUser(card1);

        // then
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getId()).isEqualTo(comment2.getId());
        assertThat(comments.get(1).getId()).isEqualTo(comment1.getId());
    }

}