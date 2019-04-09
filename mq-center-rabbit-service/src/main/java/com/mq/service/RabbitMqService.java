package com.mq.service;

/**
 * 功能：rabbit mq service
 * @author sunpeng
 * @date 2019
 */
public interface RabbitMqService {
    void pushDeadLineMqMsg(String exchange, String queue, Object content, Long intervalSecond);
    void pushDeadLineMqMsgByMsgId(String exchange, String queue, Long msgId, Long intervalSecond);
}