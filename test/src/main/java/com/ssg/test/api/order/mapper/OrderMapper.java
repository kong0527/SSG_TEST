package com.ssg.test.api.order.mapper;

import com.ssg.test.api.order.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 주문 생성
     * @param orderCreateDetailDTO
     * @param orderNum
     */
    void insertOrderInfo(@Param("or") OrderCreateDetailDTO orderCreateDetailDTO, @Param("orderNum") Long orderNum);

    /**
     * 결제 생성
     * @param payDtlDTO
     */
    void insertPayInfo(PayDtlDTO payDtlDTO);

    /**
     * 주문 상품 정보 조회
     * @param orderNum
     * @return
     */
    List<OrderListGoodsDTO> getOrderGoods(Long orderNum);

    /**
     * 취소시 결제 정보 업데이트
     * @param payDtlDTO
     * @param cancelAmount
     */
    void updatePayInfo(@Param("pay") PayDtlDTO payDtlDTO, @Param("cancelAmount") Long cancelAmount);

    /**
     * 결제 총 금액 조회
     * @param orderNum
     * @return
     */
    Long getRemainOrderPrice(Long orderNum);

    /**
     * 재고 환원
     * @param goodsId
     */
    void updateGoodsStock(@Param("goodsId") Long goodsId, @Param("qty") Integer qty);
}
