package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsInfoDTO {

    @Schema(description = "상품 재고")
    private String stockYn;

    @Schema(description = "판매 가격")
    private Long salePrice;

    @Schema(description = "할인 금액")
    private Long discountPrice;

    @Schema(description = "상품 명")
    private String goodsNm;

    @Schema(description = "상품 ID")
    private Long goodsId;
}
