package com.mq.data.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 功能：Rabbit消息队列相关常量
 * @author sunpeng
 * @date 2019
 */
public interface RabbitMqConstant {
    // 系统默认自带死信交换机
    String DEFAULT_EXCHANGE = "system_default_exchange";

    // 系统默认自带死信队列（也是交换机默认对应路由键）
    // 队列编号：0，即：0号死信队列
    // 备注：此队列不能直接配置被消费
    String DEFAULT_DEAD_QUEUE = "system_default_dl_queue";
    // 系统默认自带死信转发队列（也是交换机默认对应路由键）
    String DEFAULT_DEAD_QUEUE_REDIRECT = "system_default_dl_redirect_queue";

    List<Integer> QUEUE_NUM_LIST = Arrays.asList(0);
}