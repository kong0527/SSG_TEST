package com.ssg.test.api.order.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatusEnum {

    ORDER_COMPLETE("100", "주문 완료")
    , ORDER_CANCEL("200", "주문 취소")
    ;

    private final String status;
    private final String description;

    public String status() {
        return status;
    }

    public String description() {
        return description;
    }
}
