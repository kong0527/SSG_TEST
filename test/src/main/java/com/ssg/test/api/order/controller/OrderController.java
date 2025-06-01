package com.ssg.test.api.order.controller;

import com.ssg.test.api.order.domain.OrderCreateReqDTO;
import com.ssg.test.api.order.domain.OrderCreateRespDTO;
import com.ssg.test.api.order.service.OrderService;
import com.ssg.test.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResponse<OrderCreateRespDTO> orderCreate(@Valid @RequestBody OrderCreateReqDTO orderCreateReqDTO) {

        return orderService.orderCreate(orderCreateReqDTO);
    }
}
