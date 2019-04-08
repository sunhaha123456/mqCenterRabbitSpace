package com.mq.service;

import com.mq.common.data.base.PageList;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.data.to.request.ThirdPlatformBuildMqMsgRequest;

/**
 * 功能：mq msg service
 * @author sunpeng
 * @date 2019
 */
public interface MqMsgManageService {
    PageList<TbMqMsg> search(MqMsgSearchRequest param) throws Exception;
    TbMqMsg queryDetail(Long id);
    void handBuildMqMsg(TbMqMsg mqMsg);
    String handPushMqMsg(Long id);
    void thirdPlatformBuildMqMsg(ThirdPlatformBuildMqMsgRequest param);
}