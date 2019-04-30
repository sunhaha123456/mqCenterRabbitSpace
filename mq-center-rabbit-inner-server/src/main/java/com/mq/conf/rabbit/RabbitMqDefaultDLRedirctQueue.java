package com.mq.conf.rabbit;

import com.mq.data.constant.RabbitMqConstant;
import com.mq.service.RabbitMqService;
import com.mq.service.ThirdPlatformService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Slf4j
@Component
public class RabbitMqDefaultDLRedirctQueue {

    @Inject
    private ThirdPlatformService thirdPlatformService;
    @Inject
    private RabbitMqService rabbitMqService;

    /**
     * 功能：系统默认自带死信转发队列-回调方法
     * 备注：1、此处禁止抛Exception
     *       2、多台机器同时处理时候，一个消息只会被处理一次
     */
    @RabbitListener(queues = RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT)
    public void process(Message message, Channel channel, String msgId) {
        try {
            boolean synchFlag = true; // 同步请求标志，true：推送成功，false：发送失败
            try {
                synchFlag = thirdPlatformService.synchRequestByMsgId(Long.valueOf(msgId), true);
            } catch (Exception e1) {
                synchFlag = false;
            }
            boolean ackFlag = true;
            try {
                log.info("死信转发队列：{}，消息：{}，系统接收成功，发送ack【开始】", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // false：告诉服务器已接受到当前信息，不需要再重发了
                log.info("死信转发队列：{}，消息：{}，系统接收成功，发送ack【成功】", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId);
            } catch (Exception e2) {
                log.error("死信转发队列：{}，消息：{}，系统接收成功，发送ack【失败】（系统不再进行此消息处理，只有此服务器重启后才会进行），异常信息：{}", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId, e2);
                ackFlag = false;
            }
            if (!synchFlag && ackFlag) {
                try {
                    rabbitMqService.pushDelayedTimeMqMsg(RabbitMqConstant.DEFAULT_EXCHANGE, RabbitMqConstant.DEFAULT_DEAD_QUEUE, msgId, 30L);
                } catch (Exception e3) {
                    try {
                        log.error("死信转发队列：{}，消息：{}，系统接收成功，发送ack成功，请求远程地址失败，再次放入消息队列中失败，进行补偿机制【开始】，异常信息：{}", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId, e3);
                        rabbitMqService.pushDelayedTimeMqMsg(RabbitMqConstant.DEFAULT_EXCHANGE, RabbitMqConstant.DEFAULT_DEAD_QUEUE, msgId, 30L);
                        log.error("死信转发队列：{}，消息：{}，系统接收成功，发送ack成功，请求远程地址失败，再次放入消息队列中失败，进行补偿机制【成功】", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId);
                    } catch (Exception e4) {
                        log.error("死信转发队列：{}，消息：{}，系统接收成功，发送ack成功，请求远程地址失败，再次放入消息队列中失败，进行补偿机制【失败】（此消息需要人工介入），异常信息：{}", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId, e4);
                    }
                }
            }
        } catch (Throwable e) {
            log.error("死信转发队列：{}，消息：{}，系统异常，系统将不进行任何处理，异常信息：{}", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgId, e);
        }
    }
}