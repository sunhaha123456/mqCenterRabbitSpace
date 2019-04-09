package com.mq.conf.rabbit;

import com.mq.data.constant.RabbitMqConstant;
import com.mq.data.entity.TbMqMsg;
import com.mq.dbopt.repository.TbMqMsgRepository;
import com.mq.service.ThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
@Component
public class RabbitMqDefaultDLRedirctQueue {

    @Inject
    private TbMqMsgRepository tbMqMsgRepository;
    @Inject
    private ThirdPlatformService thirdPlatformService;

    /**
     * 功能：系统默认自带死信转发队列-回调方法
     * 备注：该方法不能抛Exception，否则会死循环
     *       多台机器同时处理时候，一个消息只会被处理一次
     * @param msg
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT)
    public void process(Object msg) {
        try {
            Long id = Long.valueOf(msg + "");
            Optional<TbMqMsg> tbMqMsgOptional = tbMqMsgRepository.findById(id);
            if (tbMqMsgOptional.isPresent()) {
                TbMqMsg res = tbMqMsgOptional.get();
                if (res.getStatus() != 2 && res.getTotalPushCount() < 3) {
                    try {
                        thirdPlatformService.defaultRemotePostPushForSuccess(res, true);
                    } catch (Exception e1) {
                        log.error("死信转发队列：{}，消息：{}，004-该消息推送失败", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msg);
                        try {

                        } catch (Exception e2) {
                            log.error("死信转发队列：{}，消息：{}，004-该消息失败后，数据库记录失败", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msg);
                        }
                    }
                } else {
                    log.error("死信转发队列：{}，消息：{}，003-该消息不符合推送条件", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msg);
                }
            } else {
                log.error("死信转发队列：{}，消息：{}，002-消息表中查无对应记录", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msg);
            }
        } catch (Exception e) {
            log.error("死信转发队列：{}，消息：{}，001-解析错误", RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT, msg);
        }
    }
}