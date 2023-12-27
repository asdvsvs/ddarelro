package com.b3.ddarelro.domain.column.dto.response;

import lombok.Builder;

@Builder
public record ColumnDeleteRes(String title, Boolean deleted) {

}
