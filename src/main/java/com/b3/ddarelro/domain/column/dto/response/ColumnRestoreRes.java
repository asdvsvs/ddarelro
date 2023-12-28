package com.b3.ddarelro.domain.column.dto.response;

import lombok.Builder;

@Builder
public record ColumnRestoreRes(String title, Boolean deleted) {

}
