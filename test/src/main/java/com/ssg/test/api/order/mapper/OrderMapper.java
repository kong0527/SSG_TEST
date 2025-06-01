package com.ssg.test.api.order.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    /**
     * 재고 보유 여부 조회
     * @param goodsId
     * @return
     */
    String getStockYn(Long goodsId);
}
