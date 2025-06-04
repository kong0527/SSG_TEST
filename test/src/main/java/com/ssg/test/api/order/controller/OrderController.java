package com.ssg.test.api.order.controller;

import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.api.order.domain.OrderListRespDTO;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 주문 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "주문 Controller", description = "주문 생성/취소/목록 조회 RESTful API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성 API", description = "주문을 생성한다.")
    public CommonResponse<OrderCreateRespDTO> orderCreate(@RequestBody List<OrderCreateReqDTO> orderCreateReqDTO) {

        return orderService.orderCreate(orderCreateReqDTO);
    }

    @GetMapping
    @Operation(summary = "주문 목록 조회 API", description = "주문 목록을 조회한다.")
    public CommonResponse<OrderListRespDTO> getOrderList(@RequestParam("orderNum") Long orderNum) {

        return orderService.getOrderList(orderNum);
    }
}
