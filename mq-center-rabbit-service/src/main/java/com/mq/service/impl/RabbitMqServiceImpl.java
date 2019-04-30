package com.mq.service.impl;

import com.mq.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Slf4j
@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Inject
    private RabbitTemplate rabbitTemplate;
    //@Inject
    //private TbMqMsgRepository tbMqMsgRepository;

    @Override
    public void pushDelayedTimeMqMsg(String exchange, String queue, Object msg, Long intervalSecond) {
        // 设置手动模式，使推送失败消息返回到队列中
        rabbitTemplate.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        /*
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        */

        // 消息确认, yml需要配置 publisher-confirms: true
        /*
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });
        */

        MessagePostProcessor processor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setContentEncoding("UTF-8");
            messageProperties.setExpiration(String.valueOf(intervalSecond * 1000)); // 延迟毫秒数
            return message;
        };
        rabbitTemplate.convertAndSend(exchange, queue, msg, processor);
    }

    // ------ 待删除
    /*
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
            pushDeadLineMqMsg(exchange, queue, msgId, intervalSecond);
        } else {
            log.error("pushDeadLineMqMsgByMsgId方法，msgId:{}，003-对应消息不符合推送条件，不再进行推送，默认做吃掉处理", msgId);
        }
    }
    */
}