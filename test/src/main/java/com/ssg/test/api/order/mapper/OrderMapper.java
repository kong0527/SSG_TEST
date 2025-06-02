package com.ssg.test.api.order.mapper;

import com.ssg.test.api.order.domain.GoodsInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    /**
     * 재고 보유 여부 조회
     * @param goodsId
     * @return
     */
    GoodsInfoDTO getGoodsInfo(Long goodsId);


}
