package com.lloop.order.service;


import com.lloop.order.config.RabbitMQConfig;
import com.lloop.order.domain.UserOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author lloop
 * @Create 2025/3/10 15:17
 */
@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    // 因为定制了 rabbitTemplate,所以不要直接使用Autowired注入默认实现
    public OrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createOrder(String userId) {
        String orderId = UUID.randomUUID().toString();
        UserOrder order = new UserOrder(userId, orderId);
        System.out.println("创建订单:" + order.toString());
        // 发送到交换机
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                order
        );
    }


}
