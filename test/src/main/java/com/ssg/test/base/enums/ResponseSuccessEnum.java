package com.ssg.test.base.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseSuccessEnum {

    SUCCESS("0000", "정상 입니다.")
    ;

    private final String resultCode;
    private final String resultMessage;

    public String resultCode() {
        return resultCode;
    }

    public String resultMessage() {
        return resultMessage;
    }
}