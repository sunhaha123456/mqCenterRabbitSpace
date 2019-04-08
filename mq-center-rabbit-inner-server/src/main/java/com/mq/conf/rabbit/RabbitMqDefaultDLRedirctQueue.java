package com.mq.conf.rabbit;

import com.mq.data.constant.RabbitMqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqDefaultDLRedirctQueue {

    /**
     * 功能：系统默认自带死信转发队列-回调方法
     * 备注：该方法不能抛Exception，否则会死循环
     *       多台机器同时处理时候，一个消息只会被处理一次
     * @param msg
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstant.DEFAULT_DEAD_QUEUE_REDIRECT)
    public void process(String msg) {




        System.out.println("死信转发队列收到消息：" + msg);
    }
}