package com.mq.data.to.request;

import com.mq.data.base.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MqMsgSearchRequest extends PageParam {
    private String startDate;
    private String endDate;
    private String requestPushPlatform;
    private String requestPushMsgContent;
    private String pushStatus; // 0：未推送 1：推送失败 2：推送成功
    private Long start;
}