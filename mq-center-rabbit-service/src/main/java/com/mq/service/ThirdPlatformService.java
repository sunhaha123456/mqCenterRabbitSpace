package com.mq.service;

import com.mq.data.entity.TbMqMsg;

public interface ThirdPlatformService {
    void defaultRemotePostPush(String destAddr, String content, boolean returnLogFlag);
    void defaultRemotePostPushByMsg(Long msgId, boolean returnLogFlag);
}