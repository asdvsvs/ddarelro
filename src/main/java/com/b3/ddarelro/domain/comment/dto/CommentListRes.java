package com.b3.ddarelro.domain.comment.dto;

import lombok.Builder;

@Builder
public record CommentListRes(Long id, String content) {

}
