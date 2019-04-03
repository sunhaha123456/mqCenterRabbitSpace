package com.mq.service.impl;

import com.mq.data.constant.SystemConstant;
import com.mq.common.repository.RedisRepositoryCustom;
import com.mq.common.util.JsonUtil;
import com.mq.data.to.vo.UserRedisVo;
import com.mq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private RedisRepositoryCustom redisRepositoryCustom;

    @Override
    public void userRedisInfoSave(String redisKey, UserRedisVo userRedis) {
        redisRepositoryCustom.saveMinutes(redisKey, JsonUtil.objectToJson(userRedis), SystemConstant.TOKEN_SAVE_TIME);
    }
}