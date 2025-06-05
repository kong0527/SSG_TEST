package com.ssg.test.api.order.service;

import com.ssg.test.api.order.domain.*;
import com.ssg.test.base.response.CommonResponse;

import java.util.List;

public interface OrderService {

    /**
     * 주문 생성
     * @param orderCreateReqDTO
     * @return
     */
    CommonResponse<OrderCreateRespDTO> orderCreate(List<OrderCreateReqDTO> orderCreateReqDTO);

    /**
     * 주문 목록 조회
     * @param orderNum
     * @return
     */
    CommonResponse<OrderListRespDTO> getOrderList(Long orderNum);

    /**
     * 주문 취소
     * @param orderCancelReqDTO
     * @return
     */
    CommonResponse<OrderCancelRespDTO> orderCancel(OrderCancelReqDTO orderCancelReqDTO);
}
