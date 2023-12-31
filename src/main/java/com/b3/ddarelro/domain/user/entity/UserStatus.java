package com.b3.ddarelro.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    PENDING("대기 중"),
    ACTIVE("활성화"),
    WITHDRAWN("탈퇴");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
