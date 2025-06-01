package com.ssg.test.api.order.service.impl;

import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.api.order.mapper.OrderMapper;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.enums.ResponseEnum;
import com.ssg.test.base.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    /**
     * 주문 생성
     * @param orderCreateReqDTO
     * @return
     */
    @Override
    public CommonResponse<OrderCreateRespDTO> orderCreate(OrderCreateReqDTO orderCreateReqDTO) {

        // 상품 정보 검색
        String stockYn = Optional.ofNullable(orderMapper.getStockYn(orderCreateReqDTO.getGoodsId())).orElse("N");

        // 재고가 없으면 주문 불가
        if ("N".equals(stockYn)) {
            return CommonResponse.fail(ResponseEnum.STOCK_ZERO, null);  // 재고가 부족합니다.
        }

        return CommonResponse.success(ResponseEnum.SUCCESS, null);  // 정상 입니다.
    }
}
