package com.mq.service.impl;

import com.mq.data.entity.TbMqMsg;
import com.mq.dbopt.repository.TbMqMsgRepository;
import com.mq.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Inject
    private AmqpTemplate rabbitTemplate;
    @Inject
    private TbMqMsgRepository tbMqMsgRepository;

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

    @Override
    public void pushDeadLineMqMsgByMsgId(String exchange, String queue, Long msgId, Long intervalSecond) {
        Optional<TbMqMsg> tbMqMsgOptional = tbMqMsgRepository.findById(msgId);
        if (!tbMqMsgOptional.isPresent()) {
            log.error("pushDeadLineMqMsgByMsgId方法，msgId:{}，001-查无对应数据记录，不再进行推送，默认做吃掉处理", msgId);
            return;
        }
        TbMqMsg msg = tbMqMsgOptional.get();
        if (msg == null) {
            log.error("pushDeadLineMqMsgByMsgId方法，msgId:{}，002-查无对应数据记录，不再进行推送，默认做吃掉处理", msgId);
            return;
        }
        if (msg.getStatus() != 2 && msg.getTotalPushCount() < 3) {
            pushDeadLineMqMsg(exchange, queue, msg.getRequestPushMsgContent(), intervalSecond);
        } else {
            log.error("pushDeadLineMqMsgByMsgId方法，msgId:{}，003-对应消息不符合推送条件，不再进行推送，默认做吃掉处理", msgId);
        }
    }
}