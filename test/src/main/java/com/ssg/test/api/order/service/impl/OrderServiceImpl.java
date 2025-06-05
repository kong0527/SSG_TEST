package com.ssg.test.api.order.service.impl;

import com.ssg.test.api.order.domain.*;
import com.ssg.test.api.order.enums.OrderStatusEnum;
import com.ssg.test.api.order.mapper.OrderMapper;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.enums.ResponseFailEnum;
import com.ssg.test.base.enums.ResponseSuccessEnum;
import com.ssg.test.base.response.CommonResponse;
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
        Long totalPrice = 0L;

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
                totalPrice += (goodsInfo.getSalePrice() - goodsInfo.getDiscountPrice());
            }
        }

        // 주문번호 채번 규칙: yyyyMMdd + 시퀀스
        String orderNumSeq = orderMapper.getOrderNumSeq();
        Long orderNum = Long.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()) + orderNumSeq);

        // 주문 생성
        orderCreateList.forEach(list -> {
            orderMapper.insertOrderInfo(list, orderNum);
        });

        // 결제 데이터 생성
        orderMapper.insertPayInfo(PayDtlDTO.builder().orderNum(orderNum).paySeq(1).payAmount(totalPrice).build());

        // TODO: 재고 차감 하다 오류 발생하면 주문 생성 실패
        // TODO: 재고 차감, 재고 체크 로직 생각 필요
        // TODO: resp DATA

        return CommonResponse.success(ResponseSuccessEnum.SUCCESS, null);  // 정상 입니다.
    }

    /**
     * 주문 목록 조회
     * @param orderNum
     * @return
     */
    @Override
    public CommonResponse<OrderListRespDTO> getOrderList(Long orderNum) {

        // 주문 상품 정보 조회
        List<OrderListGoodsDTO> orderListGoods = orderMapper.getOrderGoods(orderNum);

        // 주문 전체 금액 계산
        Long totalPrice = 0L;

        for (OrderListGoodsDTO dto : orderListGoods) {

            // 주문 상태와 주문 상태 한글 명 매핑
            OrderStatusEnum orderStatusEnum = Arrays.stream(OrderStatusEnum.values())
                    .filter(stat -> stat.status().equals(dto.getOrderStatus()))
                    .findAny().orElse(null);
            if (!ObjectUtils.isEmpty(orderStatusEnum)) {
                dto.setOrderStatusNm(orderStatusEnum.description());
            }
        }

        if (ObjectUtils.isEmpty(orderListGoods)) {
            return CommonResponse.fail(ResponseFailEnum.ORDER_INCORRECT, null); // 주문 정보가 존재하지 않습니다.
        }

        OrderListRespDTO resp = OrderListRespDTO.builder()
                .orderGoodsList(orderListGoods)
                .totalPrice(orderMapper.getRemainOrderPrice(orderNum))
                .build();

        return CommonResponse.success(ResponseSuccessEnum.SUCCESS, resp);
    }

    /**
     * 주문 취소
     * @param orderCancelReqDTO
     * @return
     */
    @Transactional
    @Override
    public CommonResponse<OrderCancelRespDTO> orderCancel(OrderCancelReqDTO orderCancelReqDTO) {

        Long orderNum = orderCancelReqDTO.getOrderNum();

        // 주문 상품 정보 조회
        List<OrderListGoodsDTO> orderListGoods = orderMapper.getOrderGoods(orderNum);

        if (ObjectUtils.isEmpty(orderListGoods)) {
            return CommonResponse.fail(ResponseFailEnum.ORDER_INCORRECT, null); // 주문 정보가 존재하지 않습니다.
        }

        // 주문 목록 중 해당 상품 ID로 취소 가능한 목록 추출
        OrderListGoodsDTO cancelGoods = orderListGoods.stream()
                .filter(dto -> OrderStatusEnum.ORDER_COMPLETE.status().equals(dto.getOrderStatus()) && Objects.equals(dto.getGoodsId(), orderCancelReqDTO.getGoodsId()))
                .toList().getFirst();

        if (ObjectUtils.isEmpty(cancelGoods)) {
            return CommonResponse.fail(ResponseFailEnum.ORDER_ALREADY_CANCEL, null); // 이미 취소된 상품입니다.
        }

        Long cancelAmount = cancelGoods.getOrderQty() * cancelGoods.getGoodsPrice();

        // 결제 테이블 업데이트
        orderMapper.updatePayInfo(PayDtlDTO.builder().orderNum(orderNum).paySeq(1).build(), cancelAmount);

        // 재고 환원
        orderMapper.updateGoodsStock(orderCancelReqDTO.getGoodsId(), cancelGoods.getOrderQty());

        OrderCancelRespDTO resp = OrderCancelRespDTO.builder()
                .cancelGoodsId(orderCancelReqDTO.getGoodsId())
                .cancelAmount(cancelAmount)
                .cancelGoodsNm(cancelGoods.getGoodsNm())
                .remainPrice(orderMapper.getRemainOrderPrice(orderNum))
                .build();

        return CommonResponse.success(ResponseSuccessEnum.SUCCESS, resp);
    }
}
