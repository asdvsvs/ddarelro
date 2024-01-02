package com.b3.ddarelro.domain.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

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
import com.b3.ddarelro.domain.comment.repository.CommentRepository;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.service.UserService;
import com.b3.ddarelro.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    UserService userService;

    @Mock
    CardService cardService;

    @Mock
    CommentRepository commentRepository;

    User user;

    Card card;

    @BeforeEach
    void init() {
        user = User.builder()
            .email("abc@naver.com")
            .username("abc")
            .password("1234")
            .build();
        card = Card.builder()
            .color("BLUE")
            .description("des")
            .name("name")
            .build();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(card, "id", 1L);
    }

    @Nested
    class 생성_테스트 {

        @Test
        void 생성_성공_테스트() {
            // given
            CommentCreateReq req = new CommentCreateReq("content", 1L);
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content(req.content())
                .build();
            ReflectionTestUtils.setField(comment, "id", 1L);
            given(commentRepository.save(any())).willReturn(comment);

            // when
            CommentCreateRes result = commentService.createComment(req, 1L);

            // then
            /**
             * CommentService에서 save 할 때 comment로 받아야 함
             */
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.content()).isEqualTo("content");
        }
    }

    @Nested
    class 수정_테스트 {

        @Test
        void 수정할_때_논리삭제가_된_상태면_에러를_반환한다() {
            // given
            CommentUpdateReq req = new CommentUpdateReq("내용수정");
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content(req.content())
                .build();
            ReflectionTestUtils.setField(comment, "deleted", true);
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));

            // when
            // then
            assertThatThrownBy(() -> commentService.updateComment(1L, req, 1L))
                .isInstanceOf(GlobalException.class).hasMessage("존재하지 않는 댓글입니다.");
        }

        @Test
        void 수정할_때_댓글이_존재하지_않으면_에러를_반환한다() {
            // given
            CommentUpdateReq req = new CommentUpdateReq("내용수정");

            // when
            // then
            assertThatThrownBy(() -> commentService.updateComment(1L, req, 1L))
                .isInstanceOf(GlobalException.class);
        }

        @Test
        void 수정할_때_본인의_댓글이_아니면_에러를_반환한다() {
            // given
            CommentUpdateReq req = new CommentUpdateReq("내용수정");
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content(req.content())
                .build();
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));

            // when
            // then
            assertThatThrownBy(() -> commentService.updateComment(1L, req, 2L))
                .isInstanceOf(GlobalException.class).hasMessage("본인의 댓글만 수정 및 삭제가 가능합니다.");
        }

        @Test
        void 성공_테스트() {
            // given
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content("내용")
                .build();
            ReflectionTestUtils.setField(comment, "id", 1L);
            CommentUpdateReq req = new CommentUpdateReq("내용수정");
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));

            // when
            CommentUpdateRes result = commentService.updateComment(1L, req, 1L);

            // then
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.content()).isEqualTo(req.content());
        }
    }

    @Nested
    class 삭제_테스트 {

        @Test
        void 삭제할_때_논리삭제가_된_상태면_에러를_반환한다() {
            // given
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content("삭제가 필요한 댓글")
                .build();
            ReflectionTestUtils.setField(comment, "deleted", true);
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));

            // when
            // then
            assertThatThrownBy(() -> commentService.deleteComment(1L, 1L))
                .isInstanceOf(GlobalException.class).hasMessage("존재하지 않는 댓글입니다.");

        }

        @Test
        void 삭제할_때_댓글이_존재하지_않으면_에러를_반환한다() {
            // given
            // when
            // then
            assertThatThrownBy(() -> commentService.deleteComment(1L, 1L))
                .isInstanceOf(GlobalException.class).hasMessage("존재하지 않는 댓글입니다.");
        }

        @Test
        void 삭제할_때_본인의_댓글이_아니면_에러를_반환한다() {
            // given
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content("삭제가 필요한 댓글")
                .build();
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));

            // when
            // then
            assertThatThrownBy(() -> commentService.deleteComment(1L, 2L))
                .isInstanceOf(GlobalException.class).hasMessage("본인의 댓글만 수정 및 삭제가 가능합니다.");
        }

        @Test
        void 성공_테스트() {
            // given
            Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content("삭제가 필요한 댓글")
                .build();
            given(commentRepository.findById(any())).willReturn(Optional.of(comment));
            // when
            CommentDeleteRes result = commentService.deleteComment(1L, 1L);

            // then
            assertThat(result.message()).isEqualTo("댓글 삭제 성공");
        }
    }

    @Nested
    class 조회_테스트 {

        @Test
        void 성공_테스트() {
            // given
            CommentListReq req = new CommentListReq(1L);
            List<Comment> comments = new ArrayList<>();
            Comment comment1 = Comment.builder()
                .user(user)
                .card(card)
                .content("조회 1")
                .build();
            Comment comment2 = Comment.builder()
                .user(user)
                .card(card)
                .content("조회 2")
                .build();
            ReflectionTestUtils.setField(comment1, "createdAt", LocalDateTime.now());
            ReflectionTestUtils.setField(comment2, "createdAt", LocalDateTime.now());
            comments.add(comment1);
            comments.add(comment2);
            given(commentRepository.findAllByFetchJoinUser(any())).willReturn(comments);

            // when
            List<CommentListRes> result = commentService.getComments(req);

            // then
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0).content()).isEqualTo("조회 1");
            assertThat(result.get(1).content()).isEqualTo("조회 2");
        }
    }

}