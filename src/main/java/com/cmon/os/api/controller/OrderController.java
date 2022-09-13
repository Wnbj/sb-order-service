package com.cmon.os.api.controller;

import com.cmon.os.api.common.TransactionRequest;
import com.cmon.os.api.common.TransactionResponse;
import com.cmon.os.api.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) {

        return orderService.saveOrder(request);
    }
}
