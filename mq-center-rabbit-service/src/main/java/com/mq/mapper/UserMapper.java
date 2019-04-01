package com.mq.mapper;

import com.mq.data.model.User;

public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long id);
}