package com.mq.service;

import com.mq.data.entity.TbMqMsg;

public interface ThirdPlatformService {
    void defaultRemotePostPush(String destAddr, String content, boolean returnLogFlag);

    /**
     * 功能：true：已处理成功 false：推送失败
     * @param msgId
     * @param returnLogFlag
     * @return
     */
    boolean defaultRemotePostPushByMsg(Long msgId, boolean returnLogFlag);
}