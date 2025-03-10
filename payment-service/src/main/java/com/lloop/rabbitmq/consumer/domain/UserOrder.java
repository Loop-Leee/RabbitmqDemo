package com.lloop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author lloop
 * @Create 2025/3/10 15:28
 */
public class UserOrder implements Serializable {

    String userId;

    String orderId;

    // 无参构造方法（Jackson 需要）
    public UserOrder() {}

    // 全参构造方法（手动创建实例时需要）
    public UserOrder(String userId, String orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
