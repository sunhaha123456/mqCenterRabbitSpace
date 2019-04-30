package com.mq.service;

/**
 * 功能：rabbit mq service
 * @author sunpeng
 * @date 2019
 */
public interface RabbitMqService {

    void pushDelayedTimeMqMsg(String exchange, String queue, Object msg, Long intervalSecond);

    // ------ 待删除
    //void pushDeadLineMqMsgByMsgId(String exchange, String queue, Long msgId, Long intervalSecond);
}