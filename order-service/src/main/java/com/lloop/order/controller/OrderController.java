package com.lloop.rabbitmq.controller;

import com.lloop.rabbitmq.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lloop
 * @Create 2025/3/10 15:25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam String userId) {
        orderService.createOrder(userId);
        return "订单创建成功!";
    }

}
