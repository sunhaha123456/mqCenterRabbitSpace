package com.mq.dbopt.mapper;

import com.mq.data.entity.TbMqMsg;
import com.mq.data.to.request.MqMsgSearchRequest;

import java.util.List;

public interface TbMqMsgMapper {
    /**
     * 功能：条件查询计数
     * @return
     */
    long countByOption(MqMsgSearchRequest param);

    /**
     * 功能：条件查询
     * @return
     */
    List<TbMqMsg> selectByOption(MqMsgSearchRequest param);
}