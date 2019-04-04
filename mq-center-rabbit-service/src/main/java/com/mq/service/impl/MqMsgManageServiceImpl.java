package com.mq.service.impl;

import com.mq.common.data.base.PageList;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class MqMsgManageServiceImpl implements MqMsgManageService {

    @Override
    public PageList<TbMqMsg> search(MqMsgSearchRequest param) throws Exception {
        PageList res = new PageList();
        res.setTotal(0);
        res.setRows(new ArrayList());
        return res;
    }
}