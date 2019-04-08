package com.mq.conf.rabbit;

import com.mq.data.constant.RabbitMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：rabbit 配置
 * @author sunpeng
 * @date 2019
 */
@Configuration
public class RabbitMqConfig {

    // 功能：定义系统默认自带死信队列交换机
    @Bean
    public DirectExchange defaultDeadLineExchange() {
        return new DirectExchange(RabbitMqConstant.DEFAULT_EXCHANGE, true, false);
    }

    // 功能：定义系统默认自带死信队列
    @Bean
    public Queue defaultDeadLineQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 声明死信交换机
        arguments.put("x-dead-letter-exchange", RabbitMqConstant.DEFAULT_EXCHANGE);
        // 声明死信路由键
        arguments.put("x-dead-letter-routing-key", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT);
        Queue queue = new Queue(RabbitMqConstant.DEFAULT_DEAD_QUEUE, true, false, false, arguments);
        return queue;
    }

    // 功能：定义系统默认自带死信转发队列
    @Bean
    public Queue defaultDeadLineRedirectQueue() {
        return new Queue(RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, true, false, false);
    }

    // 功能：绑定系统默认自带死信队列到交换机
    @Bean
    public Binding defaultDeadLineQueueBindingExchange() {
        return BindingBuilder.bind(defaultDeadLineQueue()).to(defaultDeadLineExchange()).with(RabbitMqConstant.DEFAULT_DEAD_QUEUE);
    }

    // 功能：绑定系统默认自带死信转发队列到交换机
    @Bean
    public Binding defaultDeadLineRedirectQueueBindingExchange() {
        return BindingBuilder.bind(defaultDeadLineRedirectQueue()).to(defaultDeadLineExchange()).with(RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT);
    }
}