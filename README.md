这是一个简单但是全面的 RabbitMQ 应用示例。
order-service 是生产者，会产生订单（Message），并交给订单队列。
payment-service 是消费者，会消费订单信息。

如果用户如期支付，则 payment-service 直接从订单队列中获取订单信息并处理。
如果用户超过 10 秒未支付，则消息会从订单队列转入死信队列， payment-service 会从死信队列获取过期订单信息进行处理。

安全性保证
1. 项目中使用了发布确认机制，确保 Publisher -> Exchange 的安全性。
2. RabbitMQ 会对消息进行持久化，内部安全性不用额外考虑。
3. 项目中使用了消费确认机制，确保 Queue -> Consumer 的安全性。
