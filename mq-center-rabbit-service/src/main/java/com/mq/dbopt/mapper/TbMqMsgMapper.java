package com.mq.dbopt.mapper;

import com.mq.data.entity.TbMqMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbMqMsgMapper {
    /**
     * 功能：条件查询计数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param requestPushPlatform 推送平台
     * @param requestPushMsgContent 消息内容
     * @return
     */
    long countByOption(@Param("startDate")String startDate, @Param("endDate")String endDate,
                       @Param("requestPushPlatform")Integer requestPushPlatform, @Param("requestPushMsgContent")String requestPushMsgContent);

    /**
     * 功能：条件查询
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param requestPushPlatform 推送平台
     * @param requestPushMsgContent 消息内容
     * @return
     */
    List<TbMqMsg> selectByOption(@Param("startDate")String startDate, @Param("endDate")String endDate,
                                 @Param("requestPushPlatform")Integer requestPushPlatform, @Param("requestPushMsgContent")String requestPushMsgContent);
}