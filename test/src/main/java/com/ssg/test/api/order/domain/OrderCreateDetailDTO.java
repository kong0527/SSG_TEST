package com.ssg.test.api.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderCreateDetailDTO extends GoodsInfoDTO {

/*    @Schema(description = "주문 번호")
    private Long orderNum;*/

    @Schema(description = "주문 순번")
    private Integer orderSeq;

    @Schema(description = "주문 수량")
    private Integer orderQty;

    @Schema(description = "주문 상태")
    private String orderStatus;

    @Schema(description = "주문 일자")
    private LocalDateTime orderDttm;
}
