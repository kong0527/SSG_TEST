package com.ssg.test.api.order.service.impl;

import com.ssg.test.api.order.domain.*;
import com.ssg.test.api.order.enums.OrderStatusEnum;
import com.ssg.test.api.order.mapper.OrderMapper;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.enums.ResponseFailEnum;
import com.ssg.test.base.enums.ResponseSuccessEnum;
import com.ssg.test.base.response.CommonResponse;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
    public CommonResponse<OrderCreateRespDTO> orderCreate(List<OrderCreateReqDTO> orderCreateReqDTO) {

        List<OrderCreateDetailDTO> orderCreateList = new ArrayList<>();
        LocalDateTime sysdate = LocalDateTime.now();
        Integer orderSeq = 1;

        for (OrderCreateReqDTO dto : orderCreateReqDTO) {
            if (ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getGoodsId()) || ObjectUtils.isEmpty(dto.getOrderQty()) || dto.getOrderQty() < 1) {
                return CommonResponse.fail(ResponseFailEnum.PARAM_ERROR, null); // 입력 파라미터 오류
            }

            // 상품 정보 조회
            GoodsInfoDTO goodsInfo = orderMapper.getGoodsInfo(dto.getGoodsId());
            if (ObjectUtils.isEmpty(goodsInfo)) {
                return CommonResponse.fail(ResponseFailEnum.GOODS_INCORRECT, null); // 상품 정보가 유효하지 않습니다.
            }

            // 상품 재고 체크
            String stockYn = Optional.ofNullable(goodsInfo.getStockYn()).orElse("N");
            if ("N".equals(stockYn)) {
                return CommonResponse.fail(ResponseFailEnum.STOCK_ZERO, null);  // 재고가 부족합니다.
            }

            // 주문 정보 DTO 생성
            for (int i = 0; i < dto.getOrderQty(); i++) {
                OrderCreateDetailDTO orderCreateDetail = new OrderCreateDetailDTO();
                BeanUtils.copyProperties(goodsInfo, orderCreateDetail);
                orderCreateDetail.setOrderDttm(sysdate);
                orderCreateDetail.setOrderStatus(OrderStatusEnum.ORDER_COMPLETE.status());
                orderCreateDetail.setOrderSeq(orderSeq++);
                orderCreateDetail.setOrderQty(1);
                orderCreateList.add(orderCreateDetail);
            }
        }

        // 주문번호 채번 규칙: yyyyMMdd + 시퀀스
        String orderNumSeq = orderMapper.getOrderNumSeq();
        Long orderNum = Long.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()) + orderNumSeq);

        // 주문 생성
        orderCreateList.forEach(list -> {
            orderMapper.insertOrderInfo(list, orderNum);
        });

        // TODO: 재고 차감 하다 오류 발생하면 주문 생성 실패
        // TODO: 재고 차감, 재고 체크 로직 생각 필요

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
