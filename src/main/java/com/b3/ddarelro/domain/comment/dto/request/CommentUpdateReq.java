package com.b3.ddarelro.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateReq(@NotBlank(message = "내용은 공백일 수 없습니다.") String content) {

}
