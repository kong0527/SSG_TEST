package com.ssg.test.api.order.service.impl;

import com.ssg.test.api.order.domain.GoodsInfoDTO;
import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.api.order.domain.OrderListRespDTO;
import com.ssg.test.api.order.mapper.OrderMapper;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.enums.ResponseFailEnum;
import com.ssg.test.base.enums.ResponseSuccessEnum;
import com.ssg.test.base.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
    @Transactional
    @Override
    public CommonResponse<OrderCreateRespDTO> orderCreate(OrderCreateReqDTO orderCreateReqDTO) {

        // 입력 파라미터 검증
        if (orderCreateReqDTO.getOrderQty() < 1) {
            return CommonResponse.fail(ResponseFailEnum.PARAM_ERROR, null); // 입력 파라미터 오류
        }

        // 상품 정보 조회
        GoodsInfoDTO goodsInfo = orderMapper.getGoodsInfo(orderCreateReqDTO.getGoodsId());
        if (ObjectUtils.isEmpty(goodsInfo)) {
            return CommonResponse.fail(ResponseFailEnum.GOODS_INCORRECT, null); // 상품 정보가 유효하지 않습니다.
        }

        // 상품 재고 체크
        String stockYn = Optional.ofNullable(goodsInfo.getStockYn()).orElse("N");
        if ("N".equals(stockYn)) {
            return CommonResponse.fail(ResponseFailEnum.STOCK_ZERO, null);  // 재고가 부족합니다.
        }

        // 주문 정보 저장


        // 재고 차감 하다 오류 발생하면 주문 생성 실패

        return CommonResponse.success(ResponseSuccessEnum.SUCCESS, null);  // 정상 입니다.
    }

    /**
     * 주문 목록 조회
     * @param orderNum
     * @return
     */
    @Override
    public CommonResponse<OrderListRespDTO> getOrderList(Long orderNum) {



        return null;
    }
}
