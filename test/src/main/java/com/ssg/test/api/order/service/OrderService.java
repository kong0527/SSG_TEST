package com.ssg.test.api.order.service;

import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.api.order.domain.OrderListRespDTO;
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
}
