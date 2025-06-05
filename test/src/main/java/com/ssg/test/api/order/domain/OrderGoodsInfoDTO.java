package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderGoodsInfoDTO {

    @Schema(description = "상품 ID")
    private Long goodsId;

    @Schema(description = "상품 실 구매 금액")
    private Long goodsOrderPrice;
}
