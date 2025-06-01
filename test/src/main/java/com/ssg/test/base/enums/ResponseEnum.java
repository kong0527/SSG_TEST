package com.ssg.test.base.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS("0000", "정상 입니다.")
    ,STOCK_ZERO("1000", "재고가 부족합니다.")
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