package com.mq.data.to.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThirdPlatformBuildMqMsgRequest {
    private String requestPushMsgContent; // 要推送的消息内容，必传，长度限制490
    private Integer requestPushPlatform; // 请求推送方平台，必传，1：xxx平台 2：yyy平台 3：zzz平台
    private String requestPushRemark; // 请求推送方备注，非必传，长度限制245
    private String requestPushDestAddr; // 请求送达地址，必传，长度限制245
    private Long requestPushIntervalSecond; // 请求间隔多少秒后送达，必传，禁止 < 3
    private Integer requestQueueNum; // 请求使用队列编号，非必传
}