package com.b3.ddarelro.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentListReq(@NotNull(message = "카드ID 값은 필수 입니다.") Long cardId) {

}
