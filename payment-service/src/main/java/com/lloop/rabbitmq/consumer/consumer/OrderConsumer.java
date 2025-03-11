package com.lloop.rabbitmq.consumer.consumer;


import com.lloop.order.domain.UserOrder;
import com.lloop.rabbitmq.consumer.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author lloop
 * @Create 2025/3/10 15:41
 */
@Component
public class OrderConsumer {

    /**
     * 从死信队列中读取消息,处理过期的支付请求
     * @param userOrder 已过期的支付订单信息
     * @param channel
     * @param message 每条消息的唯一标识符，用于在手动确认模式下确认消息已经被正确处理
     */
    @RabbitListener(queues = {RabbitmqConfig.DLX_QUEUE}, ackMode = "MANUAL")
    public void handleExpiredOrder(UserOrder userOrder, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // TODO 取消订单逻辑
            channel.basicAck(deliveryTag, false);
            System.out.println("订单超时未支付，自动取消：" + userOrder.toString());
        } catch (IOException e) {
            System.out.println("订单消息处理失败:" + e.getMessage());
            try {
                // 未处理的消息重新入队
                channel.basicNack(deliveryTag, false, true);
                System.out.println("消息重新入队");
            } catch (IOException nackEx) {
                throw new RuntimeException(nackEx);
            }
        }
    }


}
