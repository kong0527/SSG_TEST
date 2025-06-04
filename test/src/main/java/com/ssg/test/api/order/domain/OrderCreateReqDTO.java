package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateReqDTO {

    @Schema(description = "상품 ID")
    private Long goodsId;

    @Schema(description = "수량")
    private Integer orderQty;
}
