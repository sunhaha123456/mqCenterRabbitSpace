package com.mq.service.impl;

import com.mq.data.constant.RabbitMqConstant;
import com.mq.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Slf4j
@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Inject
    private AmqpTemplate rabbitTemplate;

    @Override
    public void pushDeadLineMqMsg(String exchange, String queue, Object content, Long intervalSecond) {
        MessagePostProcessor processor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setContentEncoding("utf-8");
            messageProperties.setExpiration(String.valueOf(intervalSecond * 1000)); // 延迟毫秒数
            return message;
        };
        rabbitTemplate.convertAndSend(exchange, queue, content, processor);

    }
}