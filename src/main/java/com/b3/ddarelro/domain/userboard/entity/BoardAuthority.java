package com.b3.ddarelro.domain.userboard.entity;


import lombok.Getter;

@Getter
public enum BoardAuthority {

    ADMIN("보드_관리자"),
    MEMBER("보드_멤버");


    private final String Authority;
    BoardAuthority(String Authority) {
        this.Authority = Authority;
    }

}
