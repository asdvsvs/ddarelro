package com.b3.ddarelro.domain.checklist.dto.response;

import lombok.Builder;

@Builder
public record CheckListGetDetailRes(String content, Boolean completed) {

}
