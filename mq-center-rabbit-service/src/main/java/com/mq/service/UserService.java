package com.mq.service;

import com.mq.data.to.vo.UserRedisVo;

/**
 * 功能：user service
 * @author sunpeng
 * @date 2019
 */
public interface UserService {
    void userRedisInfoSave(String redisKey, UserRedisVo userRedis);
}