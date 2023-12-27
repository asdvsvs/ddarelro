package com.b3.ddarelro.domain.column.dto.response;

import lombok.Builder;

@Builder
public record ColumnCreateRes(Long boardId, Long columnId, String title) {

}
