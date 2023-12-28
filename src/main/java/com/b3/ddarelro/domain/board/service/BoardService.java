package com.b3.ddarelro.domain.board.service;

import com.b3.ddarelro.domain.board.dto.request.BoardCreateReq;
import com.b3.ddarelro.domain.board.dto.request.BoardUpdateReq;
import com.b3.ddarelro.domain.board.dto.response.BoardCreateRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDeleteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDetailRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDropRes;
import com.b3.ddarelro.domain.board.dto.response.BoardInviteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardPriviewRes;
import com.b3.ddarelro.domain.board.dto.response.BoardUpdateRes;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.exception.BoardErrorCode;
import com.b3.ddarelro.domain.board.repository.BoardRepository;
import com.b3.ddarelro.domain.column.service.ColumnService;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.userboard.entity.BoardAuthority;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import com.b3.ddarelro.domain.userboard.repository.UserBoardRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserService userService;
    private final ColumnService columnService;

    public BoardCreateRes createBoard(Long userId, BoardCreateReq reqDto){

        User findUser = userService.findUser(userId);


        Board board = Board.builder()
            .color(reqDto.getColor())
            .name(reqDto.getName())
            .description(reqDto.getDescription())
            .build();

        UserBoard userBoard = UserBoard.builder()
            .user(findUser)
            .board(board)
            .authority(BoardAuthority.ADMIN)
            .build();

        board.addUserBoard(userBoard);

        boardRepository.save(board);

        userBoardRepository.save(userBoard);

        return new BoardCreateRes(board);
    }

    public BoardUpdateRes updateBoard(Long boardId, Long userId, BoardUpdateReq reqDto){

        User founddUser = userService.findUser(userId);
        Board foundBoard = findBoard(boardId);

        //board 작성자가 맞는지확인

        validteUserAuthority(founddUser,foundBoard);

        Board board = Board.builder()
            .color(reqDto.getColor())
            .name(reqDto.getName())
            .description(reqDto.getDescription())
            .build();

        return new BoardUpdateRes(board);
    }

    public BoardDeleteRes deleteBoard(Long boardId,Long userId){
        User founddUser = userService.findUser(userId);
        Board foundBoard = findBoard(boardId);

        //board 작성자가 맞는지확인
        validteUserAuthority(founddUser,foundBoard);

        foundBoard.deleteBoardState(true);
        //columnService.deleteAllColumn(boardId);
        return new BoardDeleteRes("삭제가 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    public List<BoardPriviewRes> getBoardList() {

        List<Board> boardList =  boardRepository.findAll();

        return boardList.stream()
            .filter(board -> board.getDeleted().equals(false))
            .map(BoardPriviewRes::new)
            .toList();

    }
    @Transactional(readOnly = true)
    public BoardDetailRes getBoardOne(Long userId, Long boardId) {

        User founddUser = userService.findUser(userId);

        Board foundBoard = findBoard(boardId);

        validateMember(founddUser,foundBoard);

        validateDeleted(foundBoard);

        return new BoardDetailRes(foundBoard);
    }

    public BoardInviteRes inviteMember(Long userId, Long boardId, Long invitedUserId){

        validateInviteOwn(userId,invitedUserId); // 자기자신을 초대하는지 체크

        User founddUser = userService.findUser(userId);

        User invitedUser = userService.findUser(invitedUserId);

        Board foundBoard = findBoard(boardId);

        //user가 board 작성자가 맞는지 확인
        validteUserAuthority(founddUser,foundBoard);

        validateExistedMember(invitedUser,foundBoard);  //초대할 사용자가 이미 초대된 멤버인지 확인


        UserBoard userBoard = UserBoard.builder()
            .user(founddUser)
            .board(foundBoard)
            .authority(BoardAuthority.MEMBER)
            .build();

        foundBoard.addUserBoard(userBoard);

        boardRepository.save(foundBoard);

        userBoardRepository.save(userBoard);

        return new BoardInviteRes("초대가 완료되었습니다.");
    }

    public BoardDropRes dropMember(Long userId, Long boardId, Long dropUserId){

        validateInviteOwn(userId,dropUserId); // 자기자신을 초대하는지 체크
        User founddUser = userService.findUser(userId);
        User dropUser = userService.findUser(dropUserId);
        Board foundBoard = findBoard(boardId);
        validteUserAuthority(founddUser,foundBoard);

        validateMember(dropUser,foundBoard); //탈퇴할 사용자가 멤버에 있는지 확인

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(dropUser,foundBoard).get();

        userBoardRepository.delete(userBoard);


        return new BoardDropRes("탈퇴처리되었습니다.");
    }


    public Board findBoard(Long id){
        Board board = boardRepository.findById(id)
            .orElseThrow(()->new GlobalException(BoardErrorCode.NOT_FOUND_BOARD));

        if(board.getDeleted()){
            throw new GlobalException(BoardErrorCode.NOT_FOUND_BOARD);
        }
        return board;
    }

    private void validteUserAuthority(User foundUser,Board foundBoard) {
        // 해당 보드,유저를 가지고있는 userBoard확인 후 권한을 가지고있는 유저인지 체크
        validateMember(foundUser,foundBoard);
        UserBoard foundUserBoard = userBoardRepository.findByUserAndBoard(foundUser,foundBoard).get();
        if(foundUserBoard.getBoardAuthority() != BoardAuthority.ADMIN){
            throw new GlobalException(BoardErrorCode.UNAUTHORIZED_ACCESS_BOARD);
        }
    }

    private void validateDeleted(Board board){ //삭제된 보드인지 확인
        if(board.getDeleted().equals(true)){
            throw new GlobalException(BoardErrorCode.ALREADY_DELETED_BOARD);
        }
    }

    private void validateMember(User user, Board board){ // 같은 user, board를 가지고있는 userboard인지 확인
        if(!userBoardRepository.existsByUserAndBoard(user,board)){
            throw new GlobalException(BoardErrorCode.NOT_BOARD_MEMBER);
        }

    }


    private void validateInviteOwn(Long userId, Long otherUserId){ //자기 자신을 초대 및 탈퇴 시키는지 확인
        if(userId.equals(otherUserId)){
            throw new GlobalException(BoardErrorCode.FORBIDDEN_INVITE_OWN);
        }
    }

    private void validateExistedMember(User user, Board board){ // 이미 가입되어있느 멤버인지 확인
        if(userBoardRepository.existsByUserAndBoard(user,board)){
            throw new GlobalException(BoardErrorCode.ALREADY_BOARD_MEMBER);
        }
    }





}
