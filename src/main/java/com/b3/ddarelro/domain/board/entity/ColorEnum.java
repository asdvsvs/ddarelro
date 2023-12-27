package com.b3.ddarelro.domain.board.entity;

import lombok.Getter;

@Getter
public enum ColorEnum {

    RED("빨간색"),
    BLACK("검정색"),
    BLUE("파란색"),
    WHIHE("하얀색"),
    YELLOW("노란색"),
    GREEN("초록색");

    private final String backgroundColor;
    ColorEnum(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }



}
