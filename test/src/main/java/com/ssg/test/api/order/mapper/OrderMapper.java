package com.ssg.test.api.order.mapper;

import com.ssg.test.api.order.domain.GoodsInfoDTO;
import com.ssg.test.api.order.domain.OrderCreateDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    /**
     * 주문 상품 정보 조회
     * @param goodsId
     * @return
     */
    GoodsInfoDTO getGoodsInfo(Long goodsId);

    /**
     * 주문 시퀀스 조회
     * @return
     */
    String getOrderNumSeq();

    void insertOrderInfo(@Param("or") OrderCreateDetailDTO orderCreateDetailDTO, @Param("orderNum") Long orderNum);
}
