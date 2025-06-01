package com.ssg.test.api.order.service;

import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.base.response.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    /**
     * 주문 생성
     * @param orderCreateReqDTO
     * @return
     */
    CommonResponse<OrderCreateRespDTO> orderCreate(OrderCreateReqDTO orderCreateReqDTO);
}
