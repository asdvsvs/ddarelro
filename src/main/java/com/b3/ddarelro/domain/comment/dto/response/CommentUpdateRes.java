package com.b3.ddarelro.domain.comment.dto.response;

import lombok.Builder;

@Builder
public record CommentUpdateRes(Long id, String content) {

}
