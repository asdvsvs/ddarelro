package com.b3.ddarelro.domain.file.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FileUploadReq(@NotNull(message = "카드ID 값은 필수 입니다.") Long cardId) {

}
