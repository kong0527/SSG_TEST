package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateReqDTO {

    @NotNull(message = "상품 ID는 필수 값 입니다.")
    @Schema(description = "상품 ID")
    private Long goodsId;

    @NotNull(message = "주문 수량은 필수 값 입니다.")
    @Schema(description = "수량")
    private Integer orderQty;
}
