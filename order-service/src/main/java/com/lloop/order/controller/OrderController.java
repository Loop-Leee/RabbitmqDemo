package com.lloop.order.controller;

import com.lloop.order.service.OrderService;
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

    // 测试方式: curl -X POST "http://localhost:8080/order/create?userId=123"
    @PostMapping("/create")
    public String createOrder(@RequestParam String userId) {
        orderService.createOrder(userId);
        return "订单创建成功!";
    }

}
