package com.mq.data.enums;

import com.mq.common.util.EnumBaseType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestPushPlatformEnum implements EnumBaseType {
    ADMIN_PLATFORM(0, "手动构建消息"),
    XXX_PLATFORM(1, "xxx平台"),
    YYY_PLATFORM(2, "yyy平台"),
    ZZZ_PLATFORM(3, "zzz平台");

    private Integer key;
    private String value;


    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}