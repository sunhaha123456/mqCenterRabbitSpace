package com.mq.service;

public interface ThirdPlatformService {

    // 功能：同步请求
    void synchRequest(String destAddr, String content, boolean returnLogFlag);

    /**
     * 功能：同步请求 true：已处理成功 false：推送失败
     * @param msgId
     * @param returnLogFlag
     * @return
     */
    boolean synchRequestByMsgId(Long msgId, boolean returnLogFlag);
}