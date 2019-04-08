package com.mq.data.constant;

/**
 * 功能：Rabbit消息队列相关常量
 * @author sunpeng
 * @date 2018
 */
public interface RabbitMqConstant {
    // 声明死信交换机
    String DEAD_EXCHANGE = "DL_EXCHANGE";
    // 声明死信路由键
    // String DEAD_KEY = "DL_KEY";
    // 声明死信队列
    String DEAD_QUEUE = "DL_QUEUE";
    // 声明死信转发队列
    String DEAD_QUEUE_REDIRECT = "DL_QUEUE_REDIRECT";
}