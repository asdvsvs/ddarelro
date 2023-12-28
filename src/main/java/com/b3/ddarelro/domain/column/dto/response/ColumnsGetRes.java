package com.b3.ddarelro.domain.column.dto.response;

import lombok.Builder;

@Builder
public record ColumnsGetRes(Long columnId, String title) {

}
