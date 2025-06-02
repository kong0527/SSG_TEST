package com.ssg.test.base.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseFailEnum {

    STOCK_ZERO("7000", "재고가 부족합니다.")
    , GOODS_INCORRECT("7001", "상품 정보가 유효하지 않습니다.")
    , PARAM_ERROR("9001", "입력 파라미터 오류")
    , UNKNOWN_ERROR("9999", "정상적으로 처리되지 않았습니다.");


    private final String resultCode;
    private final String resultMessage;

    public String resultCode() {
        return resultCode;
    }

    public String resultMessage() {
        return resultMessage;
    }
}
