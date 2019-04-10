package com.mq.conf.rabbit;

import com.mq.data.constant.RabbitMqConstant;
import com.mq.service.RabbitMqService;
import com.mq.service.ThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
     * @param msgSign
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT)
    public void process(Object msgSign) {
        try {
            Long msgId = Long.valueOf(msgSign + "");
            boolean flag = true;
            try {
                flag = thirdPlatformService.defaultRemotePostPushByMsg(msgId, true);
            } catch (Exception e1) {
                flag = false;
            } finally {
                try {
                    if (!flag) {
                        log.info("死信转发队列：{}，消息：{}，推送失败后，再次放入死信队列中【start】", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgSign);
                        rabbitMqService.pushDeadLineMqMsgByMsgId(RabbitMqConstant.DEFAULT_EXCHANGE, RabbitMqConstant.DEFAULT_DEAD_QUEUE, msgId, 5L);
                        log.info("死信转发队列：{}，消息：{}，推送失败后，再次放入死信队列中【成功】", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgSign);
                    }
                } catch (Exception e2) {
                    log.error("死信转发队列：{}，消息：{}，推送失败后，再次放入死信队列中【失败】(需人工手动介入)", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgSign);
                }
            }
        } catch (Throwable e) {
            log.error("死信转发队列：{}，消息：{}，001-解析错误，不再进行推送，默认做吃掉处理", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msgSign);
        }
    }
}