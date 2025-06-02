package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListGoodsDTO {

    @Schema(description = "상품 ID")
    private Long goodsId;

    @Schema(description = "상품 구매 수량")
    private Integer orderQty;

    @Schema(description = "상품 명")
    private String goodsNm;

    @Schema(description = "상품 실 구매 금액")
    private Long goodsPrice;
}
