package com.b3.ddarelro.domain.board.service;

import com.b3.ddarelro.domain.board.dto.request.BoardCreateReqDto;
import com.b3.ddarelro.domain.board.dto.request.BoardUpdateReqDto;
import com.b3.ddarelro.domain.board.dto.response.BoardCreateResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardDeleteResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardDetailResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardDropResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardInviteResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardPriviewResDto;
import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.board.repository.BoardRepository;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.userboard.entity.BoardAuthority;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import com.b3.ddarelro.domain.userboard.repository.UserBoardRepository;
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
    //private final UserService userService;


    public BoardCreateResDto createBoard(User user, BoardCreateReqDto reqDto){

        User findUser = userService.findUser(user.getId());


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

        return new BoardCreateResDto(board);
    }

    public BoardCreateResDto updateBoard(Long boardId, User user, BoardUpdateReqDto reqDto){

        User founddUser = userService.findUser(user.getId());
        Board foundBoard = findBoard(boardId);

        //board 작성자가 맞는지확인

        validteUserAuthority(founddUser,foundBoard);

        Board board = Board.builder()
            .color(reqDto.getColor())
            .name(reqDto.getName())
            .description(reqDto.getDescription())
            .build();

        return new BoardCreateResDto(board);
    }

    public BoardDeleteResDto deleteBoard(Long boardId,User user){
        User founddUser = userService.findUser(user.getId());
        Board foundBoard = findBoard(boardId);

        //board 작성자가 맞는지확인
        validteUserAuthority(founddUser,foundBoard);

        foundBoard.deleteBoardState(true);
        return new BoardDeleteResDto("삭제가 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    public List<BoardPriviewResDto> getBoardList() {

        List<Board> boardList =  boardRepository.findAll();

        return boardList.stream()
            .filter(board -> board.getDeleted().equals(false))
            .map(BoardPriviewResDto::new)
            .toList();

    }
    @Transactional(readOnly = true)
    public BoardDetailResDto getBoardOne(User user, Long boardId) {

        User founddUser = userService.findUser(user.getId());

        Board foundBoard = findBoard(boardId);

        validateMember(founddUser,foundBoard);

        validateDeleted(foundBoard);

        return new BoardDetailResDto(foundBoard);
    }

    public BoardInviteResDto inviteMember(User user, Long boardId, Long invitedUserId){

        validateInviteOwn(user,invitedUserId); // 자기자신을 초대하는지 체크

        User founddUser = userService.findUser(user.getId());

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

        return new BoardInviteResDto("초대가 완료되었습니다.");
    }

    public BoardDropResDto dropMember(User user, Long boardId, Long dropUserId){

        validateInviteOwn(user,dropUserId); // 자기자신을 초대하는지 체크

        User founddUser = userService.findUser(user.getId());

        User dropUser = userService.findUser(dropUserId);

        Board foundBoard = findBoard(boardId);

        validteUserAuthority(founddUser,foundBoard);


        validateMember(dropUser,foundBoard); //탈퇴할 사용자가 멤버에 있는지 확인

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(dropUser,foundBoard).get();

        userBoardRepository.delete(userBoard);


        return new BoardDropResDto("탈퇴처리되었습니다.");
    }


    public Board findBoard(Long id){
        return boardRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

    }

    private void validteUserAuthority(User foundUser,Board foundBoard) {

        validateMember(foundUser,foundBoard);
        UserBoard foundUserBoard = userBoardRepository.findByUserAndBoard(foundUser,foundBoard).get();
        if(foundUserBoard.getBoardAuthority() != BoardAuthority.ADMIN){
            throw new IllegalArgumentException("보드 수정 및 삭제 권한이 없습니다.");
        }
    }

    private void validateDeleted(Board board){
        if(board.getDeleted().equals(true)){
            throw new IllegalArgumentException("이미 삭제된 보드입니다.");
        }
    }

    private void validateMember(User user, Board board){
        if(!userBoardRepository.existsByUserAndBoard(user,board)){
            throw new IllegalArgumentException("해당유저는 현재 보드에 속한 멤버가 아닙니다.");
        }

    }


    private void validateInviteOwn(User user, Long userId){
        if(user.getId().equals(userId)){
            throw new IllegalArgumentException("자기 자신을 초대 및 탈퇴시킬 수 없습니다.");
        }
    }

    private void validateExistedMember(User user, Board board){
        if(userBoardRepository.existsByUserAndBoard(user,board)){
            throw new IllegalArgumentException("이미 초대된 멤버입니다.");
        }
    }





}
