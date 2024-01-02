package com.b3.ddarelro.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateReq(
    @NotBlank(message = "내용은 공백일 수 없습니다.") String content,
    @NotNull(message = "카드ID 값은 필수 입니다.") Long cardId) {
}
