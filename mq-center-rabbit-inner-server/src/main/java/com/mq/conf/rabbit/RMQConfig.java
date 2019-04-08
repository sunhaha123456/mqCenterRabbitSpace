package com.mq.conf.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：springboot rabbitmq 死信配置
 * @author sunpeng
 * @date 2018
 */
@Configuration
public class RMQConfig {

    /**
     * 功能：定义死信队列交换机
     * @return
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(RMQConstant.DEAD_EXCHANGE, true, false);
    }

    /**
     * 功能：定义一个死信队列
     * @return
     */
    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 声明死信交换机
        arguments.put("x-dead-letter-exchange", RMQConstant.DEAD_EXCHANGE);
        // 声明死信路由键
        arguments.put("x-dead-letter-routing-key", RMQConstant.DEAD_QUEUE_REDIRECT);
        Queue queue = new Queue(RMQConstant.DEAD_QUEUE, true, false, false, arguments);
        return queue;
    }

    /**
     * 功能：定义死信队列转发队列
     * @return
     */
    @Bean
    public Queue redirectQueue() {
        Queue queue = new Queue(RMQConstant.DEAD_QUEUE_REDIRECT,true,false,false);
        return queue;
    }

    /**
     * 功能：通过死信路由绑定死信队列到交换机
     * @return
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RMQConstant.DEAD_QUEUE);
    }

    /**
     * 功能：通过死信转发路由绑定死信转发队列到交换机
     * @return
     */
    @Bean
    public Binding redirectBinding() {
        return BindingBuilder.bind(redirectQueue()).to(deadLetterExchange()).with(RMQConstant.DEAD_QUEUE_REDIRECT);
    }
}