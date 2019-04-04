package com.mq.data.to.request;

import com.mq.data.base.PageParam;
import lombok.Data;

@Data
public class MqMsgSearchRequest extends PageParam {
    private String startDate;
    private String endDate;
    private Integer requestPushPlatform;
    private String requestPushMsgContent;
}