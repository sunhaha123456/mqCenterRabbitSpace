package com.mq.common.util;

/**
 * 功能：生产redis key工具类
 * @author sunpeng
 * @date 2019
 */
public class RedisKeyUtil {
    // 获取userId 对应redis中用户信息key
    public static String getRedisUserInfoKey(Object userId) {
        return "mq_center_admin_user_id_" + userId;
    }
}