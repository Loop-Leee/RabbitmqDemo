package com.lloop.rabbitmq.service;

import com.lloop.rabbitmq.config.RabbitMQConfig;
import com.lloop.rabbitmq.domain.Order;
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
        Order order = new Order(userId, orderId);
        System.out.println("创建订单:" + order);
        // 发送到交换机
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_QUEUE,
                order
        );
    }


}
