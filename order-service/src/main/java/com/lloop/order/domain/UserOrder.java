package com.lloop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lloop
 * @Create 2025/3/10 15:28
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    String userId;

    String orderId;

}
