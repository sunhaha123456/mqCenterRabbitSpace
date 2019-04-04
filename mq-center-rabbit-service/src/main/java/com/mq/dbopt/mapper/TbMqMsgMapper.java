package com.mq.dbopt.mapper;

import com.mq.data.entity.TbMqMsg;

public interface TbMqMsgMapper {
    TbMqMsg selectByPrimaryKey(Long id);
}