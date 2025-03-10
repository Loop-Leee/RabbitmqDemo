package com.lloop.rabbitmq.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author lloop
 * @Create 2025/3/9 21:18
 */
@Service
public class HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    public void receiveMessage(String message) {
        LOGGER.info("HelloService 收到消息：{}", message);
        System.out.println("HelloService 收到消息并进行了处理...");
    }

}
