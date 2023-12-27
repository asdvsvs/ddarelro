package com.b3.ddarelro.domain.column.dto.request;

import jakarta.validation.constraints.NotNull;

public record ColumnGetReq(@NotNull(message = "보드id가 null입니다") Long boardId) {

}
