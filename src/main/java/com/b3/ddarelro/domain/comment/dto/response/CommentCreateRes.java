package com.b3.ddarelro.domain.comment.dto.response;

import lombok.Builder;

@Builder
public record CommentCreateRes(Long id, String content) {
}
