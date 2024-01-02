package com.b3.ddarelro.domain.column.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ColumnMoveReq(@NotNull(message = "보드id가 null입니다") Long boardId,
                            @NotNull(message = "이동 할 위치가 null입니다") Long move) {

}
