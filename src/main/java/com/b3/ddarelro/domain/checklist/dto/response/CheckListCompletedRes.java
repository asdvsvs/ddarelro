package com.b3.ddarelro.domain.checklist.dto.response;

import lombok.Builder;

@Builder
public record CheckListCompletedRes(Long id, String content, Boolean completed) {

}
