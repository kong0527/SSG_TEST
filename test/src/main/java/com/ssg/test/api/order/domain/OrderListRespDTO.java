package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderListRespDTO {

    @Schema(description = "주문 상품 정보")
    private List<OrderListGoodsDTO> orderGoodsList;

    @Schema(description = "주문 전체 금액")
    private Long totalPrice;
}
