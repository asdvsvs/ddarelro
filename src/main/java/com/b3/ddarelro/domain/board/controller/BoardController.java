package com.b3.ddarelro.domain.board.controller;


import com.b3.ddarelro.domain.board.dto.request.BoardCreateReq;
import com.b3.ddarelro.domain.board.dto.response.BoardCreateRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDeleteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardDetailRes;
import com.b3.ddarelro.domain.board.dto.response.BoardInviteRes;
import com.b3.ddarelro.domain.board.dto.response.BoardPriviewRes;
import com.b3.ddarelro.domain.board.dto.response.BoardUpdateRes;
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
    public ResponseEntity<BoardCreateRes> createBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardCreateReq reqDto
        ){

        BoardCreateRes resDto = boardService.createBoard(userDetails.getUser().getId(),reqDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(resDto);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardUpdateRes> updateBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @RequestBody BoardCreateReq reqDto
    ){

        BoardUpdateRes resDto = boardService.updateBoard(boardId,userDetails.getUser().getId(),reqDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardDeleteRes> deleteBoard(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId
    ){

        BoardDeleteRes resDto = boardService.deleteBoard(boardId,userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);
    }



    @GetMapping
    public ResponseEntity<List<BoardPriviewRes>> getBoardListAll(){ //보드 목록 조회
        return ResponseEntity.status(HttpStatus.OK)
            .body(boardService.getBoardList());
    }

    @GetMapping("/boardId")
    public ResponseEntity<BoardDetailRes> getBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId
    ){ //보드 단건 조회
        BoardDetailRes resDto = boardService.getBoardOne(userDetails.getUser().getId(), boardId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }

    @PostMapping("/{boardId}/members/{userId}")
    public  ResponseEntity<BoardInviteRes> inviteMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @PathVariable Long userId
    ){

        BoardInviteRes resDto = boardService.inviteMember(userDetails.getUser().getId(),boardId,userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }
    @DeleteMapping("/{boardId}/members/{userId}")
    public  ResponseEntity<BoardInviteRes> inviteMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @PathVariable Long userId
    ){

        BoardInviteRes resDto = boardService.dropMember(userDetails.getUser().getId(),boardId,userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(resDto);

    }



}
