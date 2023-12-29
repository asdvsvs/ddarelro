package com.b3.ddarelro.domain.column.dto.response;

import lombok.Builder;

@Builder
public record ColumnMoveRes(Long ColumnId, Long priority, String title) {

}
