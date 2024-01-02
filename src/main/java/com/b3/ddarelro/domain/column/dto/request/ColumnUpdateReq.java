package com.b3.ddarelro.domain.column.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ColumnUpdateReq(@NotNull(message = "보드id가 null입니다") Long boardId,
                              @NotBlank(message = "컬럼 제목이 비었습니다.") String title) {

}
