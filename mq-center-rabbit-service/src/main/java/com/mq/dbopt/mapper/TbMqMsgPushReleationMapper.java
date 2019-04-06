package com.mq.dbopt.mapper;

import com.mq.data.entity.TbMqMsgPushReleation;

import java.util.List;

public interface TbMqMsgPushReleationMapper {
    List<TbMqMsgPushReleation> listByMqMsgId(Long id);
}