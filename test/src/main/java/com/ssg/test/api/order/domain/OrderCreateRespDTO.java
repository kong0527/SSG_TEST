package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderCreateRespDTO {

    @Schema(description = "주문 번호")
    private Long orderNum;

    @Schema(description = "주문 상품 정보")
    private List<OrderGoodsInfoDTO> orderGoods;

    @Schema(description = "주문 전체 금액")
    private Long orderPrice;

}
