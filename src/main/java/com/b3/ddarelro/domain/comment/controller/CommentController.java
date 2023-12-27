package com.b3.ddarelro.domain.comment.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.b3.ddarelro.domain.comment.dto.request.CommentCreateReq;
import com.b3.ddarelro.domain.comment.dto.request.CommentUpdateReq;
import com.b3.ddarelro.domain.comment.dto.response.CommentCreateRes;
import com.b3.ddarelro.domain.comment.dto.response.CommentUpdateRes;
import com.b3.ddarelro.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreateRes> createComment(@RequestBody CommentCreateReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentCreateRes res = commentService.createComment(req, userDetails.getUser().getId());
        return ResponseEntity.status(CREATED).body(res);
    }

    @PutMapping("/{commentsId}")
    public ResponseEntity<CommentUpdateRes> updateComment(@PathVariable Long commentsId,
        CommentUpdateReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentUpdateRes res = commentService.updateComment(commentsId, req,
            userDetails.getUser().getId());
        return ResponseEntity.ok(res);
    }
}
