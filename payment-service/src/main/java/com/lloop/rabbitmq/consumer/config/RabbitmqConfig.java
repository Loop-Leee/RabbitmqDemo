package com.lloop.rabbitmq.consumer.config;

import com.lloop.rabbitmq.consumer.domain.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lloop
 * @Create 2025/3/9 21:03
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 创建 hello 交换机
     * @return hello 交换机
     */
    @Bean
    public DirectExchange helloDirectExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.HELLO.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 创建hello队列
     * @return hello队列
     */
    @Bean
    public Queue helloQueue() {
        return QueueBuilder
                .durable(QueueEnum.HELLO.getQueue())
                .build();
    }

    /**
     * 绑定hello队列到hello交换机
     * @return Binding 对象
     */
    @Bean
    public Binding helloBinding() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloDirectExchange()).with(QueueEnum.HELLO.getRoutingKey());
    }


}
