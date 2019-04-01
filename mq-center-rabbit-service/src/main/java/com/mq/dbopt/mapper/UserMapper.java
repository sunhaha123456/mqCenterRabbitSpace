package com.mq.dbopt.mapper;

import com.mq.data.entity.TbUser;

public interface UserMapper {
    TbUser selectByPrimaryKey(Long id);
}