package com.lloop.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lloop
 * @Create 2025/3/9 21:03
 */
@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.created";

    public static final String DLX_EXCHANGE = "order.dlx.exchange";
    public static final String DLX_QUEUE = "order.dlx.queue";
    public static final String DLX_ROUTING_KEY = "order.dlx.expired";

    /**
     * 正常订单交换机
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    /**
     * 正常订单队列
     */
    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        args.put("x-message-ttl", 10000); // 10 秒钟
        return new Queue(ORDER_QUEUE, true, false, false, args);
    }

    /**
     * 绑定正常订单交换机和队列
     */
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }

    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(DLX_QUEUE, true);
    }

    /**
     * 绑定死信交换机和队列
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
    }

    /**
     *
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 使用 JSON 转换器传递消息
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 监听生产者到交换机的确认机制 (correlationdata: , ack: 消息是否成功发送给交换机, cause:没有到达交换机的原因)
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("【消息成功发送】correlationData：" + correlationData);
            } else {
                // TODO 失败消息可以存入数据库或 Redis，方便后续处理
                System.err.println("【消息发送失败】correlationData：" + correlationData + "，原因：" + cause);
            }
        });
        return rabbitTemplate;
    }



}
