package com.b3.ddarelro.domain.comment.controller;

import lombok.Builder;

@Builder
public record CommentListRes(Long id, String content) {

}
