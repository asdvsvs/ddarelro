package com.b3.ddarelro.domain.board.controller;


import com.b3.ddarelro.domain.board.dto.request.BoardCreateReqDto;
import com.b3.ddarelro.domain.board.dto.response.BoardCreateResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardDeleteResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardDetailResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardInviteResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardPriviewResDto;
import com.b3.ddarelro.domain.board.dto.response.BoardUpdateResDto;
import com.b3.ddarelro.domain.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreateResDto> createBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardCreateReqDto reqDto
        ){

        BoardCreateResDto resDto = boardService.createBoard(userDetails.getUser(),reqDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(resDto);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardUpdateResDto> updateBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @RequestBody BoardCreateReqDto reqDto
    ){

        BoardUpdateResDto resDto = boardService.updateBoard(boardId,userDetails.getUser(),reqDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardDeleteResDto> updateBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId
    ){

        BoardDeleteResDto resDto = boardService.deleteBoard(boardId,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);
    }



    @GetMapping
    public ResponseEntity<List<BoardPriviewResDto>> getBoardListAll(){ //보드 목록 조회
        return ResponseEntity.status(HttpStatus.OK)
            .body(boardService.getBoardList());
    }

    @GetMapping("/boardId")
    public ResponseEntity<BoardDetailResDto> getBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId
    ){ //보드 단건 조회
        BoardDetailResDto resDto = boardService.getBoardOne(userDetails.getUser(), boardId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }

    @PostMapping("/{boardId}/members/{userId}")
    public  ResponseEntity<BoardInviteResDto> inviteMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @PathVariable Long userId
    ){

        BoardInviteResDto resDto = boardService.inviteMember(userDetails.getUser(),boardId,userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }
    @DeleteMapping("/{boardId}/members/{userId}")
    public  ResponseEntity<BoardInviteResDto> inviteMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @PathVariable Long userId
    ){

        BoardInviteResDto resDto = boardService.dropMember(userDetails.getUser(),boardId,userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }



}
