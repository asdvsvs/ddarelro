package com.b3.ddarelro.domain.checklist.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckListCreateReq(@NotBlank(message = "내용이 공백일 수 없습니다") String content) {

}