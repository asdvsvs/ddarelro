package com.b3.ddarelro.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentListRes(Long id, String content, String username, LocalDateTime createdAt) {

}
