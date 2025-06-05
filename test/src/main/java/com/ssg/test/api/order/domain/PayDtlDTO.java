package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PayDtlDTO {

    @Schema(description = "주문 번호")
    private Long orderNum;

    @Schema(description = "결제 일련 번호")
    private Integer paySeq;

    @Schema(description = "결제 금액")
    private Long payAmount;
}
