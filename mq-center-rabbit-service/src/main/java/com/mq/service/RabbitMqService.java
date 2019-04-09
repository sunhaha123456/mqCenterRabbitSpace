package com.mq.service;

/**
 * 功能：
 *
 * @author sunpeng
 * @date 2019
 */
public interface RabbitMqService {
    void pushDeadLineMqMsg(String exchange, String queue, Object content, Long intervalSecond);
}