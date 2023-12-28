package com.b3.ddarelro.domain.board;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.b3.ddarelro.domain.board.dto.request.BoardCreateReq;
import com.b3.ddarelro.domain.board.dto.request.BoardDropReq;
import com.b3.ddarelro.domain.board.dto.request.BoardInviteReq;
import com.b3.ddarelro.domain.board.dto.request.BoardLeaveReq;
import com.b3.ddarelro.domain.board.dto.request.BoardUpdateReq;
import com.b3.ddarelro.domain.board.dto.response.BoardCreateRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDeleteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDetailRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDropRes;
import com.b3.ddarelro.domain.board.dto.response.BoardInviteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardPriviewRes;
import com.b3.ddarelro.domain.board.dto.response.BoardUpdateRes;
import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.entity.Color;
import com.b3.ddarelro.domain.board.exception.BoardErrorCode;
import com.b3.ddarelro.domain.board.repository.BoardRepository;
import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import com.b3.ddarelro.domain.userboard.entity.BoardAuthority;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import com.b3.ddarelro.domain.userboard.repository.UserBoardRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
public class BoardIntegrationTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserBoardRepository userBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;
    private User user;

    private User otherUser;

    private User otherUser2;

    private Long createdBoardId;

    @BeforeAll
    void setup() {
        // Given
        User user1 = User.builder()
            .username("팀장")
            .password("123456789")
            .email("123@naver.com")
            .build();
        user = userRepository.save(user1);

        User user2 = User.builder()
            .username("초대받을사람")
            .email("test1@test.com")
            .password("12345678")
            .build();

        otherUser = userRepository.save(user2);

        User user3 = User.builder()
            .username("초대받을사람2")
            .email("test2@test.com")
            .password("123456789")
            .build();

        otherUser2 = userRepository.save(user3);


    }


    @Test
    @Order(1)
    @DisplayName("보드 생성테스트")
    void 보드생성() {
        BoardCreateReq req = BoardCreateReq.builder()
            .color(Color.BLACK)
            .description("보드설명입니다.")
            .name("첫번째 보드")
            .build();

        User finduser = userRepository.findById(user.getId()).orElse(null);

        BoardCreateRes res = boardService.createBoard(finduser.getId(), req);
        createdBoardId = res.getId();
        assertNotNull(res);

        assertEquals("첫번째 보드", res.getName());
        assertEquals("보드설명입니다.", res.getDescription());

    }

    @Test
    @Order(2)
    @DisplayName("보드 단건조회")
    void 보드단건조회() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);

        BoardDetailRes res = boardService.getBoardOne(foundUser.getId(), createdBoardId);
        assertNotNull(res);

        assertEquals("첫번째 보드", res.getName());
        assertEquals("보드설명입니다.", res.getDescription());

    }

    @Test
    @Order(3)
    @DisplayName("유저_보드_중간테이블확인")
    void 유저_보드_중간테이블확인() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        Board foundBoard = boardRepository.findById(createdBoardId).orElse(null);

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(foundUser, foundBoard)
            .orElse(null);

        assertNotNull(userBoard);
        assertEquals(BoardAuthority.ADMIN, userBoard.getBoardAuthority()); //foundUser권한확인

    }


    @Test
    @Order(4)
    @DisplayName("보드 수정")
    void 보드수정() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);

        BoardUpdateReq req = BoardUpdateReq.builder()
            .color(Color.BLUE)
            .description("수정된보드입니다.")
            .build();

        BoardUpdateRes res = boardService.updateBoard(createdBoardId, foundUser.getId(), req);
        assertNotNull(res);

        assertEquals(Color.BLUE, res.getColor());
        assertEquals("수정된보드입니다.", res.getDescription());

    }

    @Test
    @Order(5)
    @DisplayName("보드 멤버초대")
    void 보드멤버초대() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        Board foundBoard = boardRepository.findById(createdBoardId).orElse(null);
        User invitedUser = userRepository.findById(otherUser.getId()).orElse(null);
        BoardInviteReq req = BoardInviteReq.builder()
            .userId(invitedUser.getId())
            .build();

        BoardInviteRes res = boardService.inviteMember(foundUser.getId(), createdBoardId, req);

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(invitedUser, foundBoard)
            .orElse(null);

        assertNotNull(userBoard);
        assertEquals(userBoard.getBoardAuthority(), BoardAuthority.MEMBER); //관리자가 아닌 일반멤버임

        //otherUser2를 미리 초대해놓음
        BoardInviteReq req2 = BoardInviteReq.builder()
            .userId(otherUser2.getId())
            .build();

        BoardInviteRes res2 = boardService.inviteMember(foundUser.getId(), createdBoardId, req2);


    }

    @Test
    @Order(6)
    @DisplayName("보드 멤버초대 실패 - 팀장이 아닌 사람이 초대를 시도")
    void 보드멤버초대실패1() {

        User foundUser = userRepository.findById(otherUser.getId()).orElse(null);
        BoardInviteReq req = BoardInviteReq.builder()
            .userId(otherUser2.getId())
            .build();

        GlobalException exception = assertThrows(GlobalException.class, () -> {
            boardService.inviteMember(foundUser.getId(), createdBoardId, req);

        });

        assertEquals(BoardErrorCode.UNAUTHORIZED_ACCESS_BOARD,
            exception.getErrorCode()); //팀장권한이없다
    }

    @Test
    @Order(7)
    @DisplayName("보드 멤버초대 실패 - 이미 가입된 회원을 초대")
    void 보드멤버초대실패2() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        BoardInviteReq req = BoardInviteReq.builder()
            .userId(otherUser.getId())
            .build();

        GlobalException exception = assertThrows(GlobalException.class, () -> {
            boardService.inviteMember(foundUser.getId(), createdBoardId, req);
        });

        assertEquals(BoardErrorCode.ALREADY_BOARD_MEMBER,
            exception.getErrorCode()); //팀장권한이없다
    }


    @Test
    @Order(8)
    @DisplayName("보드 멤버추방 실패 - 팀장이 아닌애가 추방시도")
    void 보드멤버추방실패1() {

        User foundUser = userRepository.findById(otherUser.getId()).orElse(null);

        BoardDropReq req = BoardDropReq.builder()
            .userId(otherUser2.getId())
            .build();

        GlobalException exception = assertThrows(GlobalException.class, () -> {
            boardService.dropMember(foundUser.getId(), createdBoardId, req);
        });

        assertEquals(BoardErrorCode.UNAUTHORIZED_ACCESS_BOARD,
            exception.getErrorCode()); //팀장권한이없다
    }

    @Test
    @Order(9)
    @DisplayName("보드 멤버추방성공")
    void 보드멤버추방성공() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        User dropUser = userRepository.findById(otherUser.getId()).orElse(null);
        Board foundBoard = boardRepository.findById(createdBoardId).orElse(null);

        BoardDropReq req = BoardDropReq.builder()
            .userId(otherUser.getId())
            .build();

        BoardDropRes res = boardService.dropMember(foundUser.getId(), createdBoardId, req);

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(dropUser, foundBoard)
            .orElse(null);

        assertNull(userBoard);

    }


    @Test
    @Order(10)
    @DisplayName("보드 탈퇴실패 - 팀장이지만 위임할 유저를 선택하지 않았을때")
    void 보드멤버탈퇴실패1() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        BoardLeaveReq req = null;
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            boardService.leaveBoard(foundUser.getId(), createdBoardId, req);
        });

        assertEquals(BoardErrorCode.REQUIRED_NEW_BOARD_ADMIN,
            exception.getErrorCode());

    }

    @Test
    @Order(11)
    @DisplayName("보드 팀장인경우 탈퇴성공")
    void 보드멤버탈퇴성공() {

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        BoardLeaveReq req = BoardLeaveReq.builder()
            .userId(otherUser2.getId())
            .build();

        Board foundBoard = boardRepository.findById(createdBoardId).orElse(null);

        boardService.leaveBoard(foundUser.getId(), createdBoardId, req);

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(foundUser, foundBoard)
            .orElse(null);

        assertNull(userBoard);

        User otherAdmin = userRepository.findById(otherUser2.getId()).orElse(null);
        UserBoard userBoard2 = userBoardRepository.findByUserAndBoard(otherAdmin, foundBoard)
            .orElse(null);

        assertEquals(BoardAuthority.ADMIN, userBoard2.getBoardAuthority());
    }

    @Test
    @Order(12)
    @DisplayName("보드삭제")
    void 보드삭제() {

        User foundUser = userRepository.findById(otherUser2.getId()).orElse(null);
        Board foundBoard = boardRepository.findById(createdBoardId).orElse(null);

        BoardDeleteRes res = boardService.deleteBoard(createdBoardId, foundUser.getId());

        Board deletedBoard = boardRepository.findById(createdBoardId).orElse(null);

        assertNotNull(deletedBoard);
        assertEquals(deletedBoard.getDeleted(), true);


    }

    @Test
    @Order(13)
    @DisplayName("보드리스트조회")
    void 보드리스트0개조회() {

        List<BoardPriviewRes> boardList = boardService.getBoardList(true, "createdAt");

        assertTrue(boardList.isEmpty());

    }


    @AfterAll
    void deleteAll() {
        userBoardRepository.deleteAll();
        userRepository.deleteAll();
        boardRepository.deleteAll();

        //transactional이 안걸려있는 같은 인스턴스를 공유하는 테스트클래스라서 repo를 수동으로 비워줘야한다.
    }


}
