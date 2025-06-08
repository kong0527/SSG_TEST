package com.ssg.test.api.order.service.impl;

import com.ssg.test.api.order.domain.*;
import com.ssg.test.base.enums.ResponseFailEnum;
import com.ssg.test.base.enums.ResponseSuccessEnum;
import com.ssg.test.base.response.CommonResponse;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    // 주문 생성 API - 상품 정보가 없는 경우
    @Test
    void orderCreateTest_GoodsIncorrect() {
        List<OrderCreateReqDTO> orderCreateList = new ArrayList<>();
        OrderCreateReqDTO orderCreateReqDto = new OrderCreateReqDTO();
        orderCreateReqDto.setGoodsId(0L);
        orderCreateReqDto.setOrderQty(1);
        orderCreateList.add(orderCreateReqDto);

        CommonResponse<OrderCreateRespDTO> response = orderService.orderCreate(orderCreateList);

        assertThat(response.getResultCode()).isEqualTo(ResponseFailEnum.GOODS_INCORRECT.resultCode());
    }

    // 주문 생성 API - 입력 파라미터 오류
    @Test
    void orderCreateTest_ParamError() {
        List<OrderCreateReqDTO> orderCreateList = new ArrayList<>();
        OrderCreateReqDTO orderCreateReqDto = new OrderCreateReqDTO();
        orderCreateReqDto.setGoodsId(null);
        orderCreateReqDto.setOrderQty(null);
        orderCreateList.add(orderCreateReqDto);

        CommonResponse<OrderCreateRespDTO> response = orderService.orderCreate(orderCreateList);

        assertThat(response.getResultCode()).isEqualTo(ResponseFailEnum.PARAM_ERROR.resultCode());
    }

    // 주문 생성 API - 정상 케이스
    @Test
    void orderCreateTest_Success() {
        List<OrderCreateReqDTO> orderCreateList = new ArrayList<>();
        OrderCreateReqDTO orderCreateReqDto1 = new OrderCreateReqDTO();
        orderCreateReqDto1.setGoodsId(1000000001L);
        orderCreateReqDto1.setOrderQty(2);
        orderCreateList.add(orderCreateReqDto1);
        OrderCreateReqDTO orderCreateReqDto2 = new OrderCreateReqDTO();
        orderCreateReqDto2.setGoodsId(1000000002L);
        orderCreateReqDto2.setOrderQty(1);
        orderCreateList.add(orderCreateReqDto2);

        CommonResponse<OrderCreateRespDTO> response = orderService.orderCreate(orderCreateList);

        assertThat(response.getResultCode()).isEqualTo(ResponseSuccessEnum.SUCCESS.resultCode());
        assertThat(response.getResultData().getOrderPrice()).isEqualTo(5100);
    }


    // 주문 목록 조회 API - 주문 번호가 존재하지 않는 경우
    @Test
    void getOrderListTest_OrderIncorrect() {

        Long orderNum = 0L;
        CommonResponse<OrderListRespDTO> response = orderService.getOrderList(orderNum);

        assertThat(response.getResultCode()).isEqualTo(ResponseFailEnum.ORDER_INCORRECT.resultCode());
    }

    // 주문 목록 조회 API - 정상 케이스
    @Test
    void getOrderListTest_Success() {

        Long orderNum = this.createOrder();

        // 주문 목록 조회 API 호출
        CommonResponse<OrderListRespDTO> response = orderService.getOrderList(orderNum);

        assertThat(response.getResultCode()).isEqualTo(ResponseSuccessEnum.SUCCESS.resultCode());
        assertThat(response.getResultData().getOrderGoodsList()).hasSize(1);
        assertThat(response.getResultData().getTotalPrice()).isEqualTo(700);
    }

    // 주문 취소 API - 주문 번호가 존재하지 않는 경우
    @Test
    void orderCancelTest_OrderIncorrect() {

        OrderCancelReqDTO reqDto = new OrderCancelReqDTO();
        reqDto.setOrderNum(0L);
        reqDto.setGoodsId(0L);

        CommonResponse<OrderCancelRespDTO> response = orderService.orderCancel(reqDto);
        assertThat(response.getResultCode()).isEqualTo(ResponseFailEnum.ORDER_INCORRECT.resultCode());
    }

    // 주문 취소 API - 이미 취소된 상품
    @Test
    void orderCancelTest_OrderAlreadyCancel() {

        // 주문 생성 API 호출
        Long orderNum = this.createOrder();

        // 주문 취소 API 호출
        OrderCancelReqDTO reqDto = new OrderCancelReqDTO();
        reqDto.setOrderNum(orderNum);
        reqDto.setGoodsId(1000000001L);
        orderService.orderCancel(reqDto);

        // 주문 취소 API 재호출
        CommonResponse<OrderCancelRespDTO> response = orderService.orderCancel(reqDto);
        assertThat(response.getResultCode()).isEqualTo(ResponseFailEnum.ORDER_ALREADY_CANCEL.resultCode());
    }

    // 주문 취소 API - 정상 케이스
    @Test
    void orderCancelTest_Success() {

        Long orderNum = this.createOrder();

        OrderCancelReqDTO reqDto = new OrderCancelReqDTO();
        reqDto.setOrderNum(orderNum);
        reqDto.setGoodsId(1000000001L);

        // 주문 취소 API 호출
        CommonResponse<OrderCancelRespDTO> response = orderService.orderCancel(reqDto);
        assertThat(response.getResultCode()).isEqualTo(ResponseSuccessEnum.SUCCESS.resultCode());
        assertThat(response.getResultData().getCancelAmount()).isEqualTo(700);
        assertThat(response.getResultData().getRemainPrice()).isEqualTo(0);
    }

    // 테스트 전 필요한 데이터 생성을 위해 주문 생성
    private Long createOrder() {
        List<OrderCreateReqDTO> orderCreateList = new ArrayList<>();
        OrderCreateReqDTO orderCreateReqDto = new OrderCreateReqDTO();
        orderCreateReqDto.setGoodsId(1000000001L);
        orderCreateReqDto.setOrderQty(1);
        orderCreateList.add(orderCreateReqDto);
        OrderCreateRespDTO orderCreateResp = orderService.orderCreate(orderCreateList).getResultData();

        return orderCreateResp.getOrderNum();
    }
}
