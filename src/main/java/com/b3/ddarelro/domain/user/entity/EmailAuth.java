package com.b3.ddarelro.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "emailAuth", timeToLive = 100) // redis 사용, 유효기간 10분
public class EmailAuth {

    @Id
    private String email;
    private String code;

    @Builder
    private EmailAuth(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
