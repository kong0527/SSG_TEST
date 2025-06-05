package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderCancelRespDTO {

    @Schema(description = "취소된 상품 ID")
    private Long cancelGoodsId;

    @Schema(description = "취소된 상품 명")
    private String cancelGoodsNm;

    @Schema(description = "환불 금액")
    private Long cancelAmount;

    @Schema(description = "취소 후 남은 전체 주문 금액")
    private Long remainPrice;
}
