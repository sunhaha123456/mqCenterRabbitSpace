package com.mq.dbopt.mapper;

import com.mq.data.entity.TbUser;

public interface TbUserMapper {
    // 备注：如果Mybatis 查询到多个返回结果，而你指定的返回不是List时，Mybatis会报错
    //       jpa 同上
    TbUser selectByPrimaryKey(Long id);
}