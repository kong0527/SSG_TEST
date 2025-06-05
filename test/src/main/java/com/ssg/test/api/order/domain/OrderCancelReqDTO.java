package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelReqDTO {

    @NotNull(message = "주문 번호는 필수 입력 값입니다.")
    @Schema(description = "주문 번호")
    private Long orderNum;

    @NotNull(message = "취소할 상품 ID는 필수 입력 값입니다.")
    @Schema(description = "취소할 상품 ID")
    private Long goodsId;
}
